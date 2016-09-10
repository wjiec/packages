#!/usr/bin/env python3

from collections import deque

d = deque([ 'B', 'C', 'D' ])

print(d)
d.append('E')
print(d)
d.appendleft('A')
print(d)
d.pop()
print(d)
d.popleft()
print(d)
