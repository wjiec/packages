#!/bin/env python3

def div(t, b):
    assert b != 0, 'Bottom number is zero'
    return t / b

if __name__ == '__main__':
    print(div(10, 2))
    print(div(10, 0))
