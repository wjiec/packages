#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import pika
import time
import uuid
import random
import logging
from utils.connection import get_durable_connection
from config import RABBITMQ_MASTER
from utils.logging import init_logging


if __name__ == '__main__':
    init_logging(logging.INFO)
    with get_durable_connection(RABBITMQ_MASTER, 'task_queue') as channel:
        while True:
            body = str(random.randint(1, 5)) + ':' + str(uuid.uuid4()) + '@python'
            sleep_time = random.randint(1, 3)
            channel.basic_publish(exchange='',
                                  routing_key='task_queue',
                                  body=body.encode('utf-8'),
                                  properties=pika.BasicProperties(delivery_mode=2))

            logging.info('[*] Send task <{}> to mq from Python, and will sleep {} seconds'.format(body, sleep_time))
            time.sleep(sleep_time)
