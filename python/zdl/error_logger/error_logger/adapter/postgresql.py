#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import psycopg2
from error_logger.adapter import base_adapter


class PostgresqlAdapter(base_adapter.BaseAdapter):
    
    def __init__(self, *args, **kwargs):
        self._args = args
        self._kwargs = kwargs
        super(PostgresqlAdapter, self).__init__()

    def create_connection(self):
        return psycopg2.connect(*self._args, **self._kwargs)
