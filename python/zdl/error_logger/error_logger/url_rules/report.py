#!/usr/bin/env python
#
# Copyright (C) 2017
import json
from error_logger.url_rules import _base_url_rule

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
            _level = error.pop('level')
            _time = error.pop('time', self.get_current_timestamp())
            _module = error.pop('module')
            _type = error.pop('type')
            _msg = error.pop('msg')
            _other_data = json.dumps(error)
            _ip = self.get_remote_ip()
            # TODO. modify this
            self._notification(_level)

            if not _level or not _time or not _module or not _type or not _msg:
                return self.jsonify(1, 'report data format invalid, '
                                       'may be loss some fields')
            sql = '''
                INSERT INTO "{source}"
                  ("level", "time", "module", "type", "msg", "ip", "other_data") 
                VALUES
                  ('{level}', to_timestamp('{time}'), '{module}', '{type}', '{msg}', '{ip}', '{other_data}');
            '''.format(
                source = source,
                level=_level, time=_time, module=_module, type=_type, msg=_msg,
                ip=_ip, other_data=_other_data)
            try:
                adapter.execute(sql)
            except Exception as e:
                print e
                return self.jsonify(2, 'insert error data error occurs, may be'
                                       ' error data invalid or server error')
        else:
            return self.jsonify(0, 'success')

    def _notification(self, error_level):
        pass
