#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
from threading import Thread, Lock


amount = 0
lock = Lock()


def worker(count):
    global amount
    for i in range(count):
        lock.acquire(True)
        amount = amount + 1
        lock.release()


if __name__ == '__main__':
    t1 = Thread(target=worker, args=(10000000,))
    t2 = Thread(target=worker, args=(20000000,))
    t3 = Thread(target=worker, args=(30000000,))

    t1.start()
    t2.start()
    t3.start()
    t3.join()

    print(amount)
