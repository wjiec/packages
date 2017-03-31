#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import smtplib
from email.mime.text import MIMEText
from error_logger.utils import logger
from error_logger.net import sms_notification

def send_email(config, to_list, subject, contents):
    _smtp_info = config.get('smtp')

    _sender_name = 'Notification<{}@{}>'.format(
        _smtp_info['username'], _smtp_info['server_name'])

    message = MIMEText(contents, _subtype='plain',_charset='utf-8')
    message['Subject'] = subject
    message['From'] = _sender_name
    message['To'] = ';'.join(to_list)

    try:
        _server = smtplib.SMTP(_smtp_info['host'], _smtp_info['port'])
        _server.login(_smtp_info['username'], _smtp_info['password'])
        _server.sendmail(_sender_name, to_list, message.as_string())
        _server.close()
    except Exception as e:
        logger.info('Send notification email error occurs, {}'.format(e))
        sms_notification.send_sms(config, None, 'Email Sender Error Occurs')
