#!/usr/bin/env python3

import hashlib

md5 = hashlib.md5()
md5.update('This is a title'.encode('utf-8'))
md5.update('This is a contents'.encode('utf-8'))
print(md5.hexdigest())

sha1 = hashlib.sha1()
sha1.update('This is a title'.encode('utf-8'))
sha1.update('This is a contents'.encode('utf-8'))
print(sha1.hexdigest())

sha256 = hashlib.sha256()
sha256.update('This is a title'.encode('utf-8'))
sha256.update('This is a contents'.encode('utf-8'))
print(sha256.hexdigest())

sha512 = hashlib.sha512()
sha512.update('This is a title'.encode('utf-8'))
sha512.update('This is a contents'.encode('utf-8'))
print(sha512.hexdigest())