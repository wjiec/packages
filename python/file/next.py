#!/usr/bin/python35

fp = open('hello.txt')

print(fp.__next__())

print(next(fp))

fp.close()
