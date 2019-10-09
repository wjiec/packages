#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
from multiprocessing import Process, Lock


lock = Lock()


def worker(count):
    for i in range(count):
        # lock.acquire(True)
        with open('amount.txt', 'r+') as w:
            amount = str(int(w.read()) + 1)
            w.seek(0)
            w.write(amount)
        # lock.release()


if __name__ == '__main__':
    with open('amount.txt', 'w') as fp:
        fp.write('0')

    p1 = Process(target=worker, args=(1000,))
    p2 = Process(target=worker, args=(2000,))
    p3 = Process(target=worker, args=(3000,))

    p1.start()
    p2.start()
    p3.start()
    p3.join()
