#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
import doctest


class Apple(object):
    """
    >>> Apple.__name__
    'Apple'
    >>> Apple().get_count()
    0
    >>> Apple().get_weight()
    """

    count: int

    def __init__(self):
        self.count = 0

    def get_count(self) -> int:
        return self.count

    def get_weight(self) -> int:
        pass


def get_number() -> int:
    """
    >>> get_number()
    0
    """
    return 0


if __name__ == '__main__':
    """
    >>> dict().__name__
    dict
    """
    doctest.testmod()
