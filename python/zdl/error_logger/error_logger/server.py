#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from __future__ import print_function, with_statement
import os
import time
import flask
import signal
import hashlib
from gevent import pywsgi
from error_logger import url_rules
from error_logger.net import ip_network
from error_logger.utils import (
    logger, daemon, generic, exceptions
)
from gevent import monkey
monkey.patch_all()


class ErrorLoggerServer(daemon.Daemon):

    def __init__(self, config, **kwargs):
        self._config = config
        self._http_server = None
        self._ip_policy = self._config.ip_policy
        self._whitelist_ip = dict()
        self._time_loop = dict()
        self._debug = config.daemon == 'debug'
        if os.name == 'nt':
            self._debug = True
            logger.wait_logger_init_msg(
                logger.warning,
                'Server running in Windows, only DEBUG mode')
        super(ErrorLoggerServer, self).__init__(self._debug,
                                                config.pid_file, **kwargs)
        self._application = flask.Flask(__name__)
        self._ip_network_init()
        self._application.before_request_funcs.setdefault(None, []).append(
            self._before_request)

    def _ip_network_init(self):
        unlimited_ip = self._ip_policy.get('unlimited')
        self._create_white_list_node('unlimited', unlimited_ip)

        _url_whitelist_ip = self._ip_policy.get('whitelist_ip')
        for url, ip in _url_whitelist_ip.items():
            self._create_white_list_node(url, ip)

    def _create_white_list_node(self, key, ip):
        if not ip:
            return
        if isinstance(ip, (str, unicode)):
            ip = generic.to_string(ip).split(',')
        elif not isinstance(ip, (tuple, list)):
            print(key, ip)
            raise exceptions.ForbiddenIpError(
                'In config.json `{}` field invalid'.format(key))

        if key not in self._whitelist_ip:
            self._whitelist_ip[key] = ip_network.IPNetwork()
        for address in ip:
            self._whitelist_ip[key].add_network(address)

    def _bind_url_rule(self):
        for rule in filter(lambda m: not m.startswith('_'), dir(url_rules)):
            rule_module = getattr(url_rules, rule)
            rule_cls_name = ''.join([w.capitalize() for w in rule.split('_')])
            try:
                rule_class = getattr(rule_module, rule_cls_name)
                url = generic.to_string(rule_class.__url__)
                methods = rule_class.__methods__
                if not isinstance(methods, (tuple, list)):
                    methods = [generic.to_string(methods)]
                self._application.add_url_rule(
                    url, rule, rule_class(self._config).callback,
                    methods=methods)
                logger.info('Bind url rule for {} => {}'.format(
                    rule, rule_class))
            except AttributeError:
                logger.warning("In module({}) object has no class {}".format(
                    rule, rule_cls_name))

    @property
    def debug_mode(self):
        return self._debug

    def run_forever(self):
        # start daemon on background
        super(ErrorLoggerServer, self).run_forever()
        # bind all url rule
        self._bind_url_rule()
        # start http server
        logger.info('Server running in {}:{}'.format(
            self._config.server, self._config.listen_port))
        try:
            _logger = pywsgi.LoggingLogAdapter(logger.logger, logger.g_level)
            self._http_server = pywsgi.WSGIServer(
                (self._config.server, self._config.listen_port),
                self._application, log=_logger)
            self._http_server.serve_forever()
        except KeyboardInterrupt:
            self._http_server.stop()
            logger.info('Bye, Never BUG')

    def start_debug(self):
        # bind all url rule
        self._bind_url_rule()
        # start http server
        self._application.run(self._config.server, self._config.listen_port,
                              debug=True)

    def stop_server(self):
        pid_file = os.path.abspath(self._config.pid_file)
        with open(pid_file, 'r') as fd:
            pid = int(fd.read())
            os.kill(pid, signal.SIGILL)

    def _before_request(self):
        self._check_ip_forbidden()
        self._check_authentication()

    def _check_ip_forbidden(self):
        url = flask.request.path
        ip_address = flask.request.remote_addr
        if url in self._whitelist_ip:
            if ip_address not in self._whitelist_ip[url]:
                flask.abort(403)
        # check request in white list
        if ip_address in self._whitelist_ip['unlimited']:
            return
        # execute ip policy
        _reset_time = self._ip_policy.get('reset_policy_time')
        if not isinstance(_reset_time, int):
            _reset_time = int(_reset_time)
        _max_request_count = self._ip_policy.get('max_report_count')
        if not isinstance(_max_request_count, int):
            _max_request_count = int(_max_request_count)

        if ip_address in self._time_loop:
            # if this ip is in forbidden list
            if self._time_loop[ip_address][2] is True:
                # if forbidden timeout, remove this forbidden
                if time.time() > self._time_loop[ip_address][1] + _reset_time:
                    # reset status information
                    self._time_loop[ip_address] = [1, time.time(), False]
                    return
                # if this ip is forbidden, than update timestamp
                self._time_loop[ip_address][1] = time.time()
                # reject his request
                flask.abort(403)
                return
            # that this ip not in forbidden list, increment request count
            if time.time() > self._time_loop[ip_address][1] + _reset_time:
                # timeout, than reset request count
                self._time_loop[ip_address][0] = 1
                # reset last request time
                self._time_loop[ip_address][1] = time.time()
                return
            # in reset count range
            self._time_loop[ip_address][0] += 1
            # must be add to forbidden list
            if self._time_loop[ip_address][0] > _max_request_count - 1:
                # update timestamp
                self._time_loop[ip_address][1] = time.time()
                # forbidden flag
                self._time_loop[ip_address][2] = True
        else:
            # client information: [count, last_request_timestamp, is_forbidden]
            self._time_loop[ip_address] = [1, time.time(), False]

    def _check_authentication(self):
        timestamp = str(flask.request.args['timestamp'])
        session = str(flask.request.args['session'])
        md5 = hashlib.md5()
        md5.update('.'.join([timestamp, self._config.get('secret')]))
        if session != md5.hexdigest():
            flask.abort(401)

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        if exc_tb and exc_type:
            raise exc_val


def create_error_logger_server(config, **kwargs):
    with ErrorLoggerServer(config, **kwargs) as server:
        _level = config.logging_level if config.logging_level else 'info'
        _level = generic.to_string(_level)
        logger.init(_level, server.debug_mode, config.log_file)
        return server
