#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import logging
from config import RABBITMQ_MASTER
from utils.logging import init_logging
from utils.connection import get_queue_connection
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


def consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
    logging.info('[x] Message {}:<{}> from {} received by Python'.format(
        properties.message_id, body.decode('utf-8'), method.routing_key))


if __name__ == '__main__':
    init_logging(logging.DEBUG)
    with get_queue_connection(RABBITMQ_MASTER, 'hello') as channel:
        channel.basic_consume(queue='hello', on_message_callback=consumer, auto_ack=True)
        logging.info('[*] Waiting for messages')
        channel.start_consuming()
