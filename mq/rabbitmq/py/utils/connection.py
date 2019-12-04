#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import pika
import contextlib
from typing import ContextManager
from pika.adapters.blocking_connection import BlockingChannel


@contextlib.contextmanager
def get_connection(host: str, queue: str) -> ContextManager[BlockingChannel]:
    connection = pika.BlockingConnection(pika.ConnectionParameters(host))
    channel = connection.channel()

    channel.queue_declare(queue=queue)
    yield channel

    connection.close()
