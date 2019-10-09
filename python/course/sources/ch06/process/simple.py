#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
import time
from multiprocessing import Process


def say_hello(name):
    for i in range(10):
        print("hello {}".format(name))
        time.sleep(1)


if __name__ == '__main__':
    process1 = Process(target=say_hello, args=('small red',))
    process2 = Process(target=say_hello, args=('small light',))

    process1.start()
    process2.start()
