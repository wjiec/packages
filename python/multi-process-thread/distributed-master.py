#!/usr/bin/env python3

import random, time, queue
from multiprocessing.managers import BaseManager

taskQueue = queue.Queue()
resultQueue = queue.Queue()

class QueueManager(BaseManager):
    pass

def returnTaskQueue():
    return taskQueue

def returnResultQueue():
    return resultQueue

if __name__ == '__main__':
    QueueManager.register('getTaskQueue', callable = returnTaskQueue)
    QueueManager.register('getResultQueue', callable = returnResultQueue)

    managers = QueueManager(address = ('127.0.0.1', 9000), authkey = b'shadow')
    managers.start()

    task = managers.getTaskQueue()
    result = managers.getResultQueue()

    for t in range(9):
        n = random.randint(0, 9999)
        print('Put task, args, %d' % n)
        task.put(n)

    print('try get result...')
    for i in range(9):
        r = result.get(timeout = 10)
        print('get result, %s' % r)

    managers.shutdown()
    print('master exit')