#!/usr/bin/env python3

import asyncio
from itertools import count

index = count(1)

async def hello():
    print(next(index), 'Hello World!')
    r = await asyncio.sleep(2)
    print(next(index), 'Hello Python!')

async def world():
    print(next(index), 'World Hello!')
    r = await asyncio.sleep(1)
    print(next(index), 'Python Hello!')

if __name__ == '__main__':
    el = asyncio.get_event_loop()
    el.run_until_complete(asyncio.wait([ hello(), world() ]))

    el.close()