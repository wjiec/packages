#!/usr/bin/python35

import os

hello = open('hello.txt', 'r')
print(hello.read())
hello.close()

print('#' * 40)
hello = open('hello.txt', 'r+')
hello.seek(0, 2)
hello.write('appent to this file by python\n')
hello.close()

hello = open('hello.txt')
print(hello.read())
hello.close()
