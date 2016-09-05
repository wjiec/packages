#!/bin/env python3
from __future__ import print_function
from functools import wraps

class ObjectMetaClass(type):
    def __new__(cls, name, bases, attrs):
        print(cls, name, bases, attrs)

        attrs['__str__'] = lambda self: '<: ' + self.__class__.__name__ + ', ' + hex(id(self)).__str__().upper() + ' :>'

        return type.__new__(cls, name, bases, attrs)

class Apple(object, metaclass = ObjectMetaClass):
    def __init__(self):
        pass

if __name__ == '__main__':
    print(Apple())
