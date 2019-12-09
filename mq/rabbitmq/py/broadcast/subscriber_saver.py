#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import logging
import contextlib
import utils.logging
from utils.connection import get_exchange_connection
from config import RABBITMQ_MASTER
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


@contextlib.contextmanager
def create_consumer(filename: str):
    with open(filename, 'a') as fp:
        def __consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
            data = json.loads(body.decode('utf-8'))
            utils.logging.info('{}@{}@{}: {}'.format(data['from'], data['producer'], data['index'], data['body']),
                               file=fp)
            ch.basic_ack(delivery_tag=method.delivery_tag)
        yield __consumer


if __name__ == '__main__':
    utils.logging.init_logging(logging.DEBUG)
    with get_exchange_connection(RABBITMQ_MASTER, 'logs_broadcast', 'fanout') as channel:
        channel.queue_declare(queue='logs_broadcast_saver', durable=True, auto_delete=True)
        channel.queue_bind(queue='logs_broadcast_saver', exchange='logs_broadcast')

        with create_consumer('broadcast.log') as consumer:
            channel.basic_consume(queue='logs_broadcast_saver', on_message_callback=consumer)
            channel.start_consuming()
