#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
from threading import Thread


amount = 0


def worker(count):
    global amount
    for i in range(count):
        amount = amount + 1


if __name__ == '__main__':
    t1 = Thread(target=worker, args=(10,))
    t2 = Thread(target=worker, args=(20,))
    t3 = Thread(target=worker, args=(30,))

    t1.start()
    t2.start()
    t3.start()
    t3.join()

    print(amount)
