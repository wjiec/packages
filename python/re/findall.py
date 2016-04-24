#!/usr/bin/python35

import re

r = r'^\d{3,4}-?\d{8}$'
s1 = '010-12345678'
s2 = '0593-12345678'
s3 = '059512345678'
s4 = '010-123456789'

print('r = %s' % r)
print(s1, re.findall(r, s1))
print(s2, re.findall(r, s2))
print(s3, re.findall(r, s3))
print(s4, re.findall(r, s4))
