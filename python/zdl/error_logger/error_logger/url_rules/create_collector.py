#!/usr/bin/env python
#
# Copyright (C) 2017 DL
from error_logger.adapter import base_adapter
from error_logger.url_rules import _base_url_rule

class CreateCollector(_base_url_rule.BaseUrlRule):

    __url__ = '/create_collector'

    __methods__ = ['POST']

    def __init__(self, config, *args, **kwargs):
        super(CreateCollector, self).__init__(config)

    def callback(self):
        try:
            adapter = self.get_adapter()  # type: base_adapter.BaseAdapter
            source = self.get_url_parameter('source')

            # make sure source table is non-exists
            sql = '''
                     SELECT count(*) from pg_class where relname = '{source}';
                  '''.format(source=source)
            if adapter.fetch_one(sql)[0] != 0:
                return self.operator_rst(1, 'source table exists')

            sql = '''CREATE TABLE IF NOT EXISTS "public"."{source}" (
                      "eid" SERIAL NOT NULL PRIMARY KEY,
                      "level" INT NOT NULL,
                      "time" timestamp NOT NULL,
                      "module" VARCHAR(64) NOT NULL,
                      "type" VARCHAR(64) NOT NULL,
                      "msg" TEXT NOT NULL,
                      "ip" inet NOT NULL,
                      "other_data" text NOT NULL
                    ) WITH OIDS ;'''.format(source=source)
            adapter.execute(sql)
            return self.operator_rst(0, 'success')
        except Exception as e:
            print(e)
            return self.operator_rst(2, 'create source table error')
