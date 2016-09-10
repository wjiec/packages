#!/usr/bin/env python3

import base64

'''
str - encode -> bytes
bytes - decode -> str

'''

e = base64.b64encode('abcdefghijklmnopqrstuvwxyz'.encode('utf-8'))
print(e, e.decode('utf-8'), base64.b64decode(e), sep = '\n')

print()

e = base64.urlsafe_b64encode('abcdefghijklmnopqrstuvwxyz'.encode('utf-8'))
print(e, e.decode('utf-8'), base64.urlsafe_b64decode(e), sep = '\n')