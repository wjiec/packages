#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import os
import sys
import atexit
import signal
from error_logger.utils import (
    logger, exceptions
)


class Daemon(object):

    def __init__(self, debug=False, pid_file=None, stdin='/dev/null',
                 stdout='/dev/null', stderr='/dev/null'):
        self._debug = debug
        self._stdin = stdin  # type: str
        self._stdout = stdout  # type: str
        self._stderr = stderr  # type: str
        if pid_file is None:
            pid_file = '/tmp/websocket_server.pid'
        self._pid_file = os.path.abspath(pid_file)  # type: str

    def run_forever(self):
        if self._debug:
            logger.warning('Debugger is active!')
            return

        if os.path.isfile(self._pid_file):
            raise exceptions.DeamonError(
                'pid file already exists, server running?')
        self._start_deamon()

    def stop(self):
        pass

    def _start_deamon(self):
        if not self._debug and (os.name == 'nt' or not hasattr(os, 'fork')):
            raise exceptions.DeamonError('Windows does not support fork')
        # double fork create a deamon
        try:
            pid = os.fork()  # fork #1
            if pid > 0:  # parent exit
                exit()
        except OSError as e:
            raise exceptions.FatalError(
                'Fork #1 error occurs, reason({})'.format(e))

        os.chdir('/')
        os.setsid()
        os.umask(0)

        try:
            pid = os.fork()  # fork #2
            if pid > 0:  # parent exit
                exit()
        except OSError as e:
            raise exceptions.FatalError(
                'Fork #2 error occurs, reason({})'.format(e))

        # redirect all std file descriptor
        sys.stdout.flush()
        sys.stderr.flush()
        _stdin = open(self._stdin, 'r')
        _stdout = open(self._stdout, 'a')
        # if require non-buffer, open mode muse be `b`
        _stderr = open(self._stderr, 'wb+', buffering=0)
        os.dup2(_stdin.fileno(), sys.stdin.fileno())
        os.dup2(_stdout.fileno(), sys.stdout.fileno())
        os.dup2(_stderr.fileno(), sys.stderr.fileno())

        # set signal handler
        signal.signal(signal.SIGTERM, self._signal_handler)
        signal.signal(signal.SIGILL, self._signal_handler)
        signal.signal(signal.SIGINT, signal.SIG_IGN)
        # register function at exit
        atexit.register(self._remove_pid_file)
        with open(self._pid_file, 'w') as fd:
            fd.write('{pid}\n'.format(pid=os.getpid()))
        logger.info('Daemon has been started')

    def _signal_handler(self, signum, frame):
        logger.info('Daemon receive an exit signal({}: {})'.format(
            signum, frame))
        self._remove_pid_file()
        exit()

    def _remove_pid_file(self):
        logger.info('Daemon has exited')
        if os.path.exists(self._pid_file):
            os.remove(self._pid_file)
