#!/bin/env python3

class Sample(object):
    def __init__(self):
        print('In Sample __init__()')

    def __enter__(self):
        print('In Sample __enter__()')
        return self

    def __exit__(self, type, value, trace):
        print('In Sample __exit__()', type, value, trace, sep = '\n>>> ')

    def __str__(self):
        return 'In Sample __str__()'

    def __repr__(self):
        return 'In Sample __repr__()'

    def makeException(self):
        return 1 / 0

def makeSample():
    return Sample()


if __name__ == '__main__':
    with open('id.py') as f:
        print(f, f.readline())

    with makeSample() as sample:
        print(type(sample), sample.makeException())
