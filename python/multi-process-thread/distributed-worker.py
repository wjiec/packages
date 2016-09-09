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

QueueManager.register('getTaskQueue', callable = returnTaskQueue)
QueueManager.register('getResultQueue', callable = returnResultQueue)

print('connect to master, 127.0.0.0')
managers = QueueManager(address = ('127.0.0.1', 9000), authkey = b'shadow')
managers.connect()

task = managers.getTaskQueue()
result = managers.getResultQueue()

for t in range(9):
    try:
        n = task.get(timeout = 5)
        print('from task get, args, %s, result, %d' % ( n, n * n ))
        time.sleep(random.random())
        result.put('RESULT %d: %d' % (n, n * n))
    except Queue.Empty as e:
        print('catch exception,', e)

print('worker exit')