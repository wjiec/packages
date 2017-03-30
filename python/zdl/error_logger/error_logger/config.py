#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from __future__ import print_function, with_statement
import os
import json
from error_logger.utils import (
    exceptions, logger, generic
)


class Config(object):

    def __init__(self, config_path=None):
        config_path = os.path.abspath(config_path)
        self._configure = dict()
        try:
            with open(config_path, 'r') as fd:
                _config = json.load(fd)  # type: dict
                for (k, v) in _config.items():
                    k = generic.to_string(k)
                    self._configure[''.join(['_', k])] = v
                logger.info('config created from {}'.format(config_path))
        except Exception as e:
            raise exceptions.ConfigError('Error occurs for {}'.format(str(e)))

    def update(self, *args, **kwargs):
        for dct in args:
            if isinstance(dct, dict):
                self.update(**dct)
        for (k, v) in kwargs.items():
            k = generic.to_string(''.join(['_', k]))
            self._configure[k] = v

    def export(self):
        return self._configure

    def _encode(self, _object):
        if isinstance(_object, (unicode, bytes)):
            return generic.to_string(_object)
        elif isinstance(_object, (tuple, list)):
            return [self._encode(_o) for _o in _object]
        elif isinstance(_object, dict):
            return \
                {self._encode(k): self._encode(v) for (k, v) in _object.items()}
        else:
            return _object

    def __getattr__(self, item):
        k = ''.join(['_', item])
        return self._encode(self._configure.get(k))
