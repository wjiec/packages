#!/usr/bin/env python3

import pickle

class Apple(object):
    pass

d = dict(a = 1, b = 2, c = 3, d = Apple())
p = pickle.dumps(d, 2)
print(type(p), p)

o = pickle.loads(p)
print(type(o), o)