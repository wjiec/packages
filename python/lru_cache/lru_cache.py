#!/usr/bin/env python
#
# Copyright (C) 2017 DouLe
#
from __future__ import print_function
import collections


class LRUCache(object):

    def __init__(self, size=16):
        self._cache_size = size
        self._value_store = {}
        self._history = collections.deque()

    def __getitem__(self, key):
        if key in self._history:
            self._history.remove(key)
        self._history.append(key)

        if len(self._history) > self._cache_size:
            last_item = self._history.pop()
            del self._value_store[last_item]

        return self._value_store[key]

    def __setitem__(self, key, value):
        if key in self._history:
            self._history.remove(key)
        self._history.append(key)

        if len(self._history) > self._cache_size:
            last_item = self._history.pop()
            del self._value_store[last_item]

    def __delitem__(self, key):
        pass

    def __len__(self):
        return len(self._value_store)

    def __contains__(self, item):
        return item in self._value_store


if __name__ == '__main__':
    cache = LRUCache(size=4)

    cache['a'] = 1
    cache['b'] = 2
    cache['c'] = 3
    cache['d'] = 4

    assert len(cache) == 4
    assert cache['c'] == 3

    cache['e'] = 5
    assert 'e' not in cache

    cache['b'] = 2
    cache['f'] = 6
    assert 'b' in cache
    assert 'c' not in cache
