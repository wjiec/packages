#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import contextlib
import utils.logging
from config import RABBITMQ_MASTER
from utils.connection import get_exchange_connection
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties


@contextlib.contextmanager
def consumer_factory(log_file: str):
    with open(log_file, 'a') as w:
        def __consumer(ch: BlockingChannel, method: Basic.Deliver, properties: BasicProperties, body: bytes):
            data = json.loads(body)
            message = '{:<16}@{:<16}@{:<4}@{:<20}: {}'.format(
                data['from'], data['producer'], data['index'], method.routing_key, data['body'])
            w.write(message + '\n')
            utils.logging.info(message)
            ch.basic_ack(delivery_tag=method.delivery_tag)
        yield __consumer


if __name__ == '__main__':
    with get_exchange_connection(RABBITMQ_MASTER, 'logs_topic', 'topic') as channel:
        channel.queue_declare(queue='logs_topic_all', durable=False, auto_delete=True)
        channel.queue_bind(queue='logs_topic_all', exchange='logs_topic', routing_key='#')

        with consumer_factory('topic.log') as consumer:
            channel.basic_consume(queue='logs_topic_all', on_message_callback=consumer, auto_ack=False)
            channel.start_consuming()
