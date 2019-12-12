#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import random
import logging
import utils.logging
from utils import connection
from config import RABBITMQ_MASTER
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


def consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
    data = json.loads(body)
    if hasattr(utils.logging, method.routing_key):
        getattr(utils.logging, method.routing_key)('{:<12} {:<8} {:<4} {:<8}: {}'.format(
            data['from'], data['producer'], data['index'], method.routing_key, data['body']))
        if random.randint(0, 20) == 1:
            return ch.basic_reject(delivery_tag=method.delivery_tag, requeue=True)
    ch.basic_ack(delivery_tag=method.delivery_tag)


if __name__ == '__main__':
    utils.logging.init_logging(logging.DEBUG)
    with connection.get_exchange_connection(RABBITMQ_MASTER, 'logs_routing', 'direct') as channel:
        channel.basic_qos(prefetch_count=1)

        channel.queue_declare('logs_routing_printer', durable=True, auto_delete=True)
        channel.queue_bind(queue='logs_routing_printer', exchange='logs_routing', routing_key='debug')
        channel.queue_bind(queue='logs_routing_printer', exchange='logs_routing', routing_key='info')
        channel.queue_bind(queue='logs_routing_printer', exchange='logs_routing', routing_key='warning')
        channel.queue_bind(queue='logs_routing_printer', exchange='logs_routing', routing_key='error')

        channel.basic_consume('logs_routing_printer', consumer, auto_ack=False)
        channel.start_consuming()
