#!/bin/env python3

from io import BytesIO

b = BytesIO()

b.write(b'\xee\xff\x00')
print(b, b.getvalue())

b = BytesIO('ABCDEF'.encode('utf-8'))
print(b, b.read())
