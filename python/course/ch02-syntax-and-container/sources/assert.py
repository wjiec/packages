#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
import abc


class Fruit(object):
    color: str  # 变量标注

    # 构造函数
    def __init__(self, color):
        self.color = color


class Apple(Fruit, metaclass=abc.ABCMeta):

    def __init__(self):
        super(Apple, self).__init__('red')

    @classmethod
    @abc.abstractmethod
    def impl_me(cls):
        pass

    @property
    def value(self):
        return 1

    @value.setter
    def value(self, v):
        self.color = v


Apple()
if __name__ == '__main__':
    assert id(1) == id(1)
    assert not isinstance(None, object)
