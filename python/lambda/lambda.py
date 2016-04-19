#!/usr/bin/python35

from functools import reduce

l = reduce(lambda x, y: x * y, range(1, 11))

print(range(1, 11), '=>', l)
