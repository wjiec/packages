#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
import time
import threading


def say_hello(name):
    for i in range(10):
        print("hello {}".format(name))


thread1 = threading.Thread(target=say_hello, args=('small red',))
thread2 = threading.Thread(target=say_hello, args=('small light',))

thread1.start()
thread2.start()
