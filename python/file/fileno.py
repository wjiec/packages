#!/usr/bin/python35

fp = open('hello.txt')
print('fp.fileno() = %s' % (fp.fileno()))
fp.close()
