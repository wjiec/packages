#!/usr/bin/env python3

import requests

url = 'http://httpbin.org/get'

response = requests.get(url)
if response.status_code is requests.codes.ok:
	print('OK', requests.codes.ok)

try:
	response = requests.get('http://httpbin.org/post')
	response.raise_for_status()
except Exception as e:
	print(e)