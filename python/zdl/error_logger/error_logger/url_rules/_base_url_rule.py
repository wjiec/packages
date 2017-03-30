#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import time
import flask
import random
from error_logger import adapter


class BaseUrlRule(object):
    # bind url string
    __url__ = ''
    # allow request methods
    __methods__ = []

    def __init__(self, config):
        self._config = config
        self._dbs = config.dbs  # type: dict

    def callback(self):
        raise NotImplementedError

    def abort(self, *args, **kwargs):
        flask.abort(*args, **kwargs)

    def jsonify(self, *args, **kwargs):
        return flask.jsonify(*args, **kwargs)

    def get_adapter(self):
        k = random.choice(list(self._dbs.keys()))
        return adapter.create_connection(k, self._dbs.get(k))

    def get_url_parameter(self, key, default=False):
        value = flask.request.args.get(key, None)
        if default is not False:
            if value is None:
                return default
        if value is not None:
            return value
        self.abort(400)

    def get_body_dict(self):
        origin_data = flask.request.json
        force_json_data = flask.request.get_json(force=True)

        if origin_data:
            return origin_data
        return force_json_data

    def get_body_parameter(self, key, default=False):
        value = self.get_body_dict().get(key, None)
        if default is not False:
            if value is None:
                return default
        if value is not None:
            return value
        self.abort(400)

    def get_remote_ip(self):
        return flask.request.remote_addr

    def get_current_timestamp(self):
        return int(time.time())

    def operator_rst(self, status_code, message):
        return self.jsonify({'status': status_code, 'message': message})