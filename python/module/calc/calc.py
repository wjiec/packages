#!/usr/bin/python35

def add(x, y):
    return x + y

def dec(x, y):
    return x - y

def div(x, y):
    if y == 0:
        return 0
    return x / y

def mult(x, y):
    return x * y

if __name__ == '__main__':
    print('Module: Calc')
