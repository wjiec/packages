#!/usr/bin/env python3

from datetime import datetime

print(type(datetime.now()), datetime.now())
print(type(datetime(2016, 1, 1, 0, 0)), datetime(2016, 1, 1, 0, 0))

print(type(datetime.now().timestamp()), datetime.now().timestamp())

ds = datetime.now().timestamp()
print(datetime.fromtimestamp(ds))

d = '2016-10-1 0:0:0'
print(datetime.strptime(d, '%Y-%m-%d %H:%M:%S'))