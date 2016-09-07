#!/usr/bin/env python3
#-*- coding: utf-8 -*-

import requests, chardet

r = requests.get('https://github.com/timeline.json')

print('status code', r.status_code)
print('headers', r.headers)
print('coding', r.encoding)
print('text', r.text)
print('json', r.json())
