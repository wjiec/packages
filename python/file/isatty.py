#!/usr/bin/python35

import sys

fp = open('hello.txt')
print('fp.isatty() = %s' % (fp.isatty()))
fp.close()

print('sys.stdin.isatty() = %s' % (sys.stdin.isatty()))
