#!/usr/bin/env python
#
# Copyright (C) 2019 Jayson
#
import asyncio
import httpx


async def main():
    client = httpx.AsyncClient()
    for i in range(1000):
        print(await client.get('http://www.baidu.com'))


if __name__ == '__main__':
    loop = asyncio.new_event_loop()
    loop.run_until_complete(main())
    loop.close()
