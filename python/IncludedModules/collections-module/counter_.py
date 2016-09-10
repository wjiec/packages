#!/usr/bin/env python3

from collections import Counter, defaultdict


class CounterCopy(defaultdict):
    def __init__(self, *key, **kwargs):
        super(CounterCopy, self).__init__(lambda: 0, *key, **kwargs)

    def __str__(self):
        return self.__class__.__name__ + '(' + dict(self).__str__() + ')'

c = Counter()
cc = CounterCopy()

for ch in 'ShadowMan':
    c[ch] += 1
    cc[ch] += 1

print(c, cc, sep = '\n')