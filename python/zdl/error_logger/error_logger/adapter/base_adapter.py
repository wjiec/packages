#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import sys
import contextlib
from error_logger.utils import exceptions, logger

if sys.version_info[0] >= 3:
    integer_types = int,
else:
    import __builtin__
    integer_types = int, __builtin__.long


class BaseAdapter(object):

    def __init__(self):
        pass

    def create_connection(self):
        raise NotImplementedError()

    @contextlib.contextmanager
    def connection(self, isolation_level=None):
        conn = self.create_connection()
        try:
            if isolation_level is not None:
                if conn.isolation_level == isolation_level:
                    isolation_level = None
                else:
                    conn.set_isolation_level(isolation_level)
            yield conn
        except:
            if conn.closed:
                conn = None
            else:
                conn = self._rollback(conn)
            raise
        else:
            if conn.closed:
                raise exceptions.OperationalError(
                    'Cannot commit because connection was closed: {}'.format(
                        conn))
            conn.commit()
        finally:
            if conn is not None and not conn.closed:
                if isolation_level is not None:
                    conn.set_isolation_level(isolation_level)

    @contextlib.contextmanager
    def cursor(self, *args, **kwargs):
        isolation_level = kwargs.pop('isolation_level', None)
        with self.connection(isolation_level) as conn:
            yield conn.cursor(*args, **kwargs)

    def _rollback(self, conn):
        try:
            conn.rollback()
        except Exception:
            logger.error('connection {} rollback error occurs'.format(conn))
            return
        return conn

    def execute(self, *args, **kwargs):
        with self.cursor(**kwargs) as cursor:
            cursor.execute(*args)
            return cursor.rowcount

    def fetch_one(self, *args, **kwargs):
        with self.cursor(**kwargs) as cursor:
            cursor.execute(*args)
            return cursor.fetchone()

    def fetch_all(self, *args, **kwargs):
        with self.cursor(**kwargs) as cursor:
            cursor.execute(*args)
            return cursor.fetchall()

    def fetch_iter(self, *args, **kwargs):
        with self.cursor(**kwargs) as cursor:
            cursor.execute(*args)

            while True:
                items = cursor.fetchmany()
                if not items:
                    break
                for item in items:
                    yield item
