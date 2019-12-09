#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import logging


class Logging(object):

    def __init__(self):
        self._level = logging.INFO

    def log(self, level, msg, *args, end='\n', file=None):
        if level >= self.level:
            print(msg.format(*args), end=end, flush=True, file=file)

    @property
    def level(self):
        return self._level

    @level.setter
    def level(self, value):
        self._level = value


__default_logger = Logging()


def init_logging(level=logging.INFO):
    __default_logger.level = level


def debug(msg, *args, **kwargs):
    __default_logger.log(logging.DEBUG, msg, *args, **kwargs)


def info(msg, *args, **kwargs):
    __default_logger.log(logging.INFO, msg, *args, **kwargs)


def warning(msg, *args, **kwargs):
    __default_logger.log(logging.WARN, msg, *args, **kwargs)


def error(msg, *args, **kwargs):
    __default_logger.log(logging.ERROR, msg, *args, **kwargs)


def fatal(msg, *args, **kwargs):
    __default_logger.log(logging.CRITICAL, msg, *args, **kwargs)
    exit(1)

