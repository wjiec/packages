#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import logging
import contextlib
import utils.logging
from utils import connection
from config import RABBITMQ_MASTER
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


@contextlib.contextmanager
def consumer_factory(log_file: str):
    with open(log_file, 'a') as w:
        def __consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
            data = json.loads(body)
            w.write('{:<16}@{:<16}@{:<4}@{:<12}: {}\n'.format(
                data['from'], data['producer'], data['index'], method.routing_key, data['body']))
            ch.basic_ack(delivery_tag=method.delivery_tag)
        yield __consumer


if __name__ == '__main__':
    utils.logging.init_logging(logging.DEBUG)
    with connection.get_exchange_connection(RABBITMQ_MASTER, 'logs_routing', 'direct') as channel:
        channel.basic_qos(prefetch_count=1)
        channel.queue_declare(queue='logs_routing_saver', durable=True, auto_delete=True)
        channel.queue_bind(queue='logs_routing_saver', exchange='logs_routing', routing_key='warning')
        channel.queue_bind(queue='logs_routing_saver', exchange='logs_routing', routing_key='error')

        with consumer_factory('routing.log') as consumer:
            channel.basic_consume('logs_routing_saver', consumer, auto_ack=False)
            channel.start_consuming()
