#!/usr/bin/env python
#
# Copyright (C) 2019 Jayson
#
import types
from queue import Queue


class Scheduler(object):

    def __init__(self):
        self._tasks = Queue()

    def main_loop(self):
        while not self._tasks.empty():
            try:
                generator = self._tasks.get()
                v = next(generator)
                if isinstance(v, types.GeneratorType):
                    self._scheduler(v)
                self._scheduler(generator)
            except StopIteration:
                pass

    def coroutine(self, func):
        def __c_wrapper(*args, **kwargs):
            self._scheduler(func(*args, **kwargs))
        return __c_wrapper

    def _scheduler(self, generator):
        self._tasks.put(generator)


if __name__ == '__main__':
    scheduler = Scheduler()

    def io(name):
        print('start io: {}'.format(name))
        yield
        print('end io: {}'.format(name))

    @scheduler.coroutine
    def task1():
        print('task1 start io operator')
        yield io('task1')
        print('task1 start io completed')


    @scheduler.coroutine
    def task2():
        print('task2 start io operator')
        yield task1()
        print('task2 start io completed')

    task1()
    task2()
    scheduler.main_loop()
