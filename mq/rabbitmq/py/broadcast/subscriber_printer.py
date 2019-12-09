#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import random
import logging
import utils.logging
from utils.connection import get_exchange_connection
from config import RABBITMQ_MASTER
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


def consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
    data = json.loads(body.decode('utf-8'))
    utils.logging.info("receive message <{}@{}>: '{}' from {}".format(
        data['producer'], data['index'], data['body'], data['from']))

    for _ in range(random.randint(1, (data['index'] % 5) + 1)):
        print('.', end='', flush=True)
        if random.randint(1, 25) == 1:
            utils.logging.warning('reject message <{}@{}>'.format(data['producer'], data['index']))
            print('rejected', flush=True)
            return ch.basic_reject(delivery_tag=method.delivery_tag)
    print('done', flush=True)
    ch.basic_ack(delivery_tag=method.delivery_tag)


if __name__ == '__main__':
    utils.logging.init_logging(logging.DEBUG)
    with get_exchange_connection(RABBITMQ_MASTER, 'logs_broadcast', 'fanout') as channel:
        channel.basic_qos(prefetch_count=1)
        channel.queue_declare(queue='logs_broadcast_printer', durable=True, auto_delete=True)
        channel.queue_bind(queue='logs_broadcast_printer', exchange='logs_broadcast')

        channel.basic_consume(queue='logs_broadcast_printer', on_message_callback=consumer)
        channel.start_consuming()
