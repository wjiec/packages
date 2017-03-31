#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from __future__ import print_function, with_statement
import os
import argparse
from error_logger import config, server


def entry_pointer():
    parser = argparse.ArgumentParser()

    parser.add_argument('-c', '--config',
                        type=str,
                        help='configure file path')
    parser.add_argument('-s', '--server',
                        type=str,
                        default='0.0.0.0',
                        help='server listen address')
    parser.add_argument('-d', '--daemon',
                        type=str,
                        choices=['start', 'stop', 'debug'],
                        help='server start mode')
    options = parser.parse_args()
    if hasattr(options, 'daemon') and hasattr(options, 'config'):
        if options.daemon is None:
            parser.error('too few arguments')
        if options.config is None:
            parser.error('too few arguments')
    _config = config.Config(options.config)
    for attr in dir(options):
        if attr.startswith('_'):
            continue
        _config.update({attr: getattr(options, attr)})
    _server = server.create_error_logger_server(_config)
    if options.daemon == 'start':
        _server.run_forever()
    elif options.daemon == 'stop':
        _server.stop_server()
    elif options.daemon == 'debug':
        _server.start_debug()

if __name__ == '__main__':
    entry_pointer()
