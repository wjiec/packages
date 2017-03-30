#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import os
import sys

def to_string(_object, encoding ='utf-8'):
    if isinstance(_object, str):
        return _object
    if isinstance(_object, bytes):
        return _object.decode(encoding)
    if sys.version_info[0] == 2 and isinstance(_object, unicode):
        return _object.encode(encoding)
    if hasattr(_object, '__str__'):
        return _object.__str__()
    raise TypeError('_object to string error occurs, _object invalid')


def flatten_list(array):
    def _flatten_list(_array):
        for item in _array:
            if isinstance(item, (tuple, list)):
                return _flatten_list(item)
            else:
                return item
    return list(_flatten_list(array))

