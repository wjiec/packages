#!/usr/bin/env python3

import asyncio, threading

@asyncio.coroutine
def hello():
    print('Hello World! [THREAD %s]' % ( threading.currentThread() ))
    r = yield from asyncio.sleep(2)
    print('Hello Python! [THREAD %s]' % ( threading.currentThread() ))

def world():
    print('World Hello! [THREAD %s]' % ( threading.currentThread() ))
    r = yield from asyncio.sleep(5)
    print('Python Hello! [THREAD %s]' % ( threading.currentThread() ))

if __name__ == '__main__':
    loop = asyncio.get_event_loop()

    loop.run_until_complete(asyncio.wait([ hello(), world() ]))

    loop.close()