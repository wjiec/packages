#!/usr/bin/env python3

import socket
import selectors

baseSelector = selectors.DefaultSelector()

if __name__ == '__main__':
    with open('../poll.py', 'r') as f:
        baseSelector.register(f, selectors.EVENT_READ)

        for k, v in baseSelector.select(0):
            print(k, v)

'''
https://docs.python.org/3/library/select.html#module-select

Note that on Windows, it only works for sockets;

'''