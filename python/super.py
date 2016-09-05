#!/bin/env python3

class A(object):
    def __init__(self):
        print('Enter Class A', self)
        print('    Leave Class A', self)

class B(A):
    def __init__(self):
        print('Enter Class B', self)
        A.__init__(self)
        print('    Leave Class B', self)

class C(A):
    def __init__(self):
        print('Enter Class C', self)
        A.__init__(self)
        print('    Leave Class C', self)

class D(A):
    def __init__(self):
        print('Enter Class D', self)
        A.__init__(self)
        print('    Leave Class D', self)

class E(B, C, D):
    def __init__(self):
        print('Enter Class D', self)
        B.__init__(self)
        C.__init__(self)
        D.__init__(self)
        print('    Leave Class D', self)

class F(B, C, D):
    def __init__(self):
        print('Enter Class F', self)
        super(F, self).__init__()
        print('    Leave Class F', self)

if __name__ == '__main__':
    E()
    print('\nUsing super()')
    F()