#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
from multiprocessing import Process


var = 0


def worker(worker_id):
    global var
    print(worker_id, id(var), var)
    var = worker_id
    print(worker_id, id(var), var)


if __name__ == '__main__':
    process1 = Process(target=worker, args=(1,))
    process2 = Process(target=worker, args=(2,))

    print(id(var), var)
    process1.start()
    process2.start()
    process2.join()
    print(id(var), var)

