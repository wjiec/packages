#!/usr/bin/python35

fp = open('hello.txt', 'r+')

print('fp.read() => \n', fp.read())

fp.seek(0, 2)
fp.write('append to this file by write.py')

fp.close()
