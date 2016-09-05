#!/bin/env python3

class A(object):
    def foo(self):
        print('Class A')

class B(A):
    pass

class C(A):
    def foo(self):
        print('Class C')

class D(B, C):
    pass


class H:
    def foo(self):
        print('Class H')

class I(H):
    pass

class J(H):
    def foo(self):
        print('Class J')

class K(I, J):
    pass


if __name__ == '__main__':
    D().foo()
    K().foo()
