#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import time
import random
import logging
from config import RABBITMQ_MASTER
from utils.logging import init_logging
from utils.connection import get_durable_connection
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


def consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
    logging.info('[x] Message {}:{}:<{}> from {} received by Python'.format(
        method.delivery_tag, properties.message_id, body.decode('utf-8'), method.routing_key))
    task_time, uuid = body.decode('utf-8').split(':')
    print('[:] Task uuid: {}'.format(uuid), end='\t')
    for _ in range(int(task_time)):
        print('.', end='', flush=True)
        time.sleep(1)
        if random.randint(0, 20) == 1:
            logging.warning('[#] Failure with worker {}'.format(method.delivery_tag))
            return ch.basic_reject(delivery_tag=method.delivery_tag)
    print('done', flush=True)
    ch.basic_ack(delivery_tag=method.delivery_tag)


if __name__ == '__main__':
    init_logging(logging.INFO)
    with get_durable_connection(RABBITMQ_MASTER, 'task_queue') as channel:
        channel.basic_consume(queue='task_queue', on_message_callback=consumer)
        logging.info('[*] Waiting for messages by Python')
        channel.start_consuming()
