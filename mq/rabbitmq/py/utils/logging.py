#!/usr/bin/env python3
#
# Copyright (C) 2019 jayson
#
import logging


def init_logging(level=logging.INFO, log_format='%(asctime)s %(levelname)s \t: %(message)s'):
    logging.basicConfig(level=level, format=log_format)
