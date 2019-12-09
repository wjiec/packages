#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
from utils.connection import get_queue_connection
from config import RABBITMQ_MASTER


if __name__ == '__main__':
    with get_queue_connection(RABBITMQ_MASTER, 'hello') as channel:
        while True:
            channel.basic_publish(exchange='', routing_key='hello', body=b'hello world from python')
