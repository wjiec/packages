#!/usr/bin/python35

fp = open('hello.txt')

print('fp.read() => \n', fp.read())

fp.seek(0, 0)
print('fp.read(8) => \n', fp.read(8))

fp.close()
