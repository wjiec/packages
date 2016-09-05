#!/bin/env python3

from io import StringIO

s = StringIO()

s.write('Hello')
s.write(' ')
s.write('World!')
print(s, s.getvalue())

si = 'Hello Python'
so = StringIO(si)
print(so, so.read())
