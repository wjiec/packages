#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import pika
import contextlib
from typing import ContextManager
from pika.adapters.blocking_connection import BlockingChannel


@contextlib.contextmanager
def get_queue_connection(host: str, queue: str) -> ContextManager[BlockingChannel]:
    with get_simple_connection(host=host) as channel:
        channel.queue_declare(queue=queue)
        yield channel


@contextlib.contextmanager
def get_durable_queue_connection(host: str, queue: str) -> ContextManager[BlockingChannel]:
    with get_simple_connection(host) as channel:
        channel.basic_qos(prefetch_count=1)
        channel.queue_declare(queue=queue, durable=True)
        yield channel


@contextlib.contextmanager
def get_exchange_connection(host: str, exchange: str, exchange_type: str) -> ContextManager[BlockingChannel]:
    with get_simple_connection(host) as channel:
        channel.exchange_declare(exchange=exchange, exchange_type=exchange_type, durable=True, auto_delete=False)
        yield channel


@contextlib.contextmanager
def get_simple_connection(host: str) -> ContextManager[BlockingChannel]:
    connection = pika.BlockingConnection(pika.ConnectionParameters(host))
    channel = connection.channel()

    yield channel

    connection.close()
