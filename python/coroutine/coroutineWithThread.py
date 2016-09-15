#!/usr/bin/env python3

from threading import Thread
from queue import Queue

def coroutine(func):
    def wrapper(*keys, **kwargs):
        c = func(*keys, **kwargs)
        c.send(None)
        return c
    return wrapper

@coroutine
def threaded(target):
    message = Queue()

    def run_target():
        while True:
            item = message.get()
            if item is GeneratorExit:
                target.close()
                return
            else:
                target.send(item)
    Thread(target = run_target).start()
    try:
        while True:
            item = (yield)
            message.put(item)
    except GeneratorExit:
        message.put(GeneratorExit)

@coroutine
def printer():
    while True:
        msg = (yield)
        print(msg)

if __name__ == '__main__':
    t = threaded(printer())
    for i in range(10):
        t.send(i)

    t.close()