#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import json
from error_logger.url_rules import _base_url_rule


class FetchLog(_base_url_rule.BaseUrlRule):

    __url__ = '/fetch_log'

    __methods__ = ['GET']

    def __init__(self, config):
        super(FetchLog, self).__init__(config)

    def callback(self):
        adapter = self.get_adapter()
        _source = self.get_url_parameter('source')  # type: str
        _level = self.get_url_parameter('level', None)
        _start_time = self.get_url_parameter('start_time', None)
        _end_time = self.get_url_parameter('end_time', None)
        _module = self.get_url_parameter('module', None)
        _type = self.get_url_parameter('type', None)
        _ip = self.get_url_parameter('ip', None)
        _limit = self.get_url_parameter('limit', None)
        _offset = self.get_url_parameter('offset', None)

        # There are SQL injection risk
        sql = 'SELECT * FROM "{source}"'.format(source=_source)
        if _level or _start_time or _end_time or _module or _type or _ip:
            sql += " WHERE"

            if _level:
                sql += " level=%s"

            if _start_time:
                if not sql.endswith('WHERE'):
                    sql += " AND"
                sql += " time>=%s"

            if _end_time:
                if not sql.endswith('WHERE'):
                    sql += " AND"
                sql += " time<=%s"

            if _module:
                if not sql.endswith('WHERE'):
                    sql += " AND"
                sql += " module=%s"

            if _ip:
                if not sql.endswith('WHERE'):
                    sql += " AND"
                sql += " ip=%s"

        if _limit:
            sql += " LIMIT %s"
        if _offset:
            sql += " OFFSET %s"
        sql += ';'
        try:
            with adapter.cursor() as cursor:
                result = map(lambda r: {
                    'eid': r[0],
                    'level': r[1],
                    'time': r[2],
                    'module': r[3],
                    'type': r[4],
                    'msg': r[5],
                    'ip': r[6],
                    'other_data': r[7]
                }, adapter.fetch_all(
                    cursor.mogrify(
                        sql,
                        filter(lambda x: x, [
                            _level,
                            _start_time,
                            _end_time,
                            _module,
                            _type,
                            _ip,
                            _limit,
                            _offset
                        ])
                    )
                ))
                return self.jsonify(result)
        except Exception:
            return self.jsonify({'status': 1, 'message': 'fetch log error occurs'})
