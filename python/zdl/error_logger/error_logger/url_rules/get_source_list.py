#!/usr/bin/env python
#
# Copyright (C) 2017 DL
from error_logger.adapter import base_adapter
from error_logger.url_rules import _base_url_rule

class GetSourceList(_base_url_rule.BaseUrlRule):

    __url__ = '/get_source_list'

    __methods__ = ['GET']

    def __init__(self, config, *args, **kwargs):
        super(GetSourceList, self).__init__(config)

    def callback(self):
        try:
            adapter = self.get_adapter()  # type: base_adapter.BaseAdapter

            sql = "SELECT tablename FROM pg_tables WHERE schemaname='public';"
            return self.jsonify([row[0] for row in adapter.fetch_all(sql)])
        except Exception as e:
            print(e)
            return self.operator_rst(1, 'get source list error, may be '
                                        'database connection error')
