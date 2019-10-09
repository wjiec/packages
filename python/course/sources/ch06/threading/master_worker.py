#!/usr/bin/env python3
#
# Copyright (C) 2019 Jayson
#
import time
import random
from threading import Thread
from queue import Queue


def worker(thread_id: int, queue: Queue):
    while True:
        data = queue.get(True)
        print("worker-{} receive task: ".format(thread_id), data)
        time.sleep(data)


if __name__ == '__main__':
    threads = []
    task_queue = Queue()
    for i in range(3):
        threads.append(Thread(target=worker, args=(i, task_queue), daemon=True))
    list(map(lambda t: t.start(), threads))

    for i in range(10):
        task_queue.put(random.randint(1, 3))
    time.sleep(10)
