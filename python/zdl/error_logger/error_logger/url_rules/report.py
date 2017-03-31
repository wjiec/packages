#!/usr/bin/env python
#
# Copyright (C) 2017
import json
import time
from error_logger.url_rules import _base_url_rule
# from error_logger.net import sms_notification, email_notification
from error_logger.utils import generic


class Report(_base_url_rule.BaseUrlRule):

    __url__ = '/report'

    __methods__ = ['POST']

    def __init__(self, config, *args, **kwargs):
        super(Report, self).__init__(config)

    def callback(self):
        adapter = self.get_adapter()
        source = self.get_url_parameter('source')  # type: str
        json_data = self.get_body_dict()  # type: dict

        for error in json_data.get('errors', []):
            _level = int(error.pop('level'))
            _time = int(error.pop('time', self.get_current_timestamp()))
            _module = generic.to_string(error.pop('module'))
            _type = generic.to_string(error.pop('type'))
            _msg = error.pop('msg')
            _other_data = json.dumps(error)
            _ip = generic.to_string(self.get_remote_ip())
            # TODO. modify this
            self._notification(source, _level)

            if not _level or not _time or not _module or not _type or not _msg:
                return self.jsonify(1, 'report data format invalid, '
                                       'may be loss some fields')
            source.replace('\'', '\'\'')
            sql = 'INSERT INTO "{source}"'.format(source=source)
            with adapter.cursor() as cursor:
                sql = cursor.mogrify(
                    '''
                    INSERT INTO "{source}"
                      ("level", "time", "module", "type", "msg", "ip", "other_data")
                    VALUES 
                      (%s, %s, %s, %s, %s, %s, %s)
                    '''.format(source=source), (_level,
                         time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(_time)),
                         _module,
                         _type,
                         _msg,
                         _ip,
                         _other_data
                    )
                )
            try:
                adapter.execute(sql)
            except Exception as e:
                print e
                return self.jsonify(2, 'insert error data error occurs, may be'
                                       ' error data invalid or server error')
        else:
            return self.jsonify(0, 'success')

    def _notification(self, source, error_level):
        pass
