#!/usr/bin/python35

fp = open('hello.txt', 'r+')

print('fp.read() => \n', fp.read())

fp.seek(0, 2)
fp.writelines(['append to this file by writelines.py\n', 'hello, nice to meet you', '**** cc\n'])

fp.close()
