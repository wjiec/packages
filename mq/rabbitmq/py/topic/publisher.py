#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import json
import random
import utils.logging
from utils.connection import get_exchange_connection
from utils.random import random_string, random_sleep
from config import RABBITMQ_MASTER


if __name__ == '__main__':
    with get_exchange_connection(RABBITMQ_MASTER, 'logs_topic', 'topic') as channel:
        name = random_string(8)
        for index in range(2 ** 32):
            module = random.choice(['account', 'base', 'statistics', 'open'])
            level = random.choice(['debug', 'info', 'warning', 'error'])
            message = json.dumps({'producer': name, 'index': index, 'from': 'python', 'body': random_string(32)})
            channel.basic_publish('logs_topic', routing_key=module + '.' + level, body=message.encode('utf-8'))
            utils.logging.info("send message<{} | {:^20} | {}> to logs_routing from python".format(
                name, module + '.' + level, index))
            random_sleep(2)
