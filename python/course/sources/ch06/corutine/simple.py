#!/usr/bin/env python
#
# Copyright (C) 2019 Jayson
#
import asyncio
import threading


@asyncio.coroutine
def hello():
    print('Hello World! [THREAD %s] 1' % (threading.currentThread()))
    yield from asyncio.sleep(2)
    print('Hello Python! [THREAD %s] 1' % (threading.currentThread()))


async def world():
    print('Hello World! [THREAD %s] 2' % (threading.currentThread()))
    await asyncio.sleep(5)
    print('Hello Python! [THREAD %s] 2' % (threading.currentThread()))


if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(asyncio.wait([hello(), world()]))
    loop.close()
