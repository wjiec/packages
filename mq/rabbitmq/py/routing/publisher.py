#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import random
import logging
import utils.logging
from utils.connection import get_exchange_connection
from utils.random import random_string, random_sleep
from config import RABBITMQ_MASTER


if __name__ == '__main__':
    utils.logging.init_logging(logging.DEBUG)
    with get_exchange_connection(RABBITMQ_MASTER, 'logs_routing', 'direct') as channel:
        name = random_string(8)
        for index in range(2 ** 32):
            level = random.choice(['debug', 'info', 'warning', 'error'])
            message = json.dumps({'producer': name, 'index': index, 'from': 'python', 'body': random_string(32)})
            channel.basic_publish(exchange='logs_routing', routing_key=level, body=message.encode('utf-8'))
            utils.logging.info("send message<{} | {} | {}> to logs_routing from python".format(name, level, index))
            random_sleep(2)
