#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import random
import utils.logging
from config import RABBITMQ_MASTER
from utils.connection import get_exchange_connection
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


def consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
    data = json.loads(body)
    utils.logging.info('{:<16}@{:<16}@{:<4}@{:<20}: {}'.format(
        data['from'], data['producer'], data['index'], method.routing_key, data['body']))
    if random.randint(0, 10) == 1:
        ch.basic_reject(delivery_tag=method.delivery_tag)
        return
    ch.basic_ack(delivery_tag=method.delivery_tag)


if __name__ == '__main__':
    with get_exchange_connection(RABBITMQ_MASTER, 'logs_topic', 'topic') as channel:
        channel.queue_declare(queue='logs_topic_statistics', durable=False, auto_delete=True)
        channel.queue_bind(queue='logs_topic_statistics', exchange='logs_topic', routing_key='statistics.*')

        channel.basic_consume(queue='logs_topic_statistics', on_message_callback=consumer, auto_ack=False)
        channel.start_consuming()
