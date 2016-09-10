#!/usr/bin/env python3

from collections import namedtuple

Position = namedtuple('Position', [ 'x', 'y' ])

p = Position(0, 0)
print(type(p), p.x, p.y)