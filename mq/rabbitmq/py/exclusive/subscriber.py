#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import time
from utils.connection import get_simple_connection
from config import RABBITMQ_MASTER


if __name__ == '__main__':
    with get_simple_connection(RABBITMQ_MASTER) as channel:
        # 405, "RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'exclusive_test' in vhost '/'
        channel.queue_declare('exclusive_test', durable=True, exclusive=True, auto_delete=True)
        time.sleep(30)
