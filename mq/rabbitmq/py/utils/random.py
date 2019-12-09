#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import time
import random
import string


def random_string(length: int) -> str:
    return ''.join(random.sample(string.ascii_letters + string.digits, length))


def random_sleep(more: int, less: int=1):
    time.sleep(random.randint(less, more))
