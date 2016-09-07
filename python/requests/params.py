#!/usr/bin/python

import requests

payload = { 'key': 'value', 'option': 'value', 'list': [ 'val1', 'val2' ] }
response = requests.get('http://httpbin.org/get', params = payload)

print(response)
for key, value in response.json().items():
	print(key, '' if isinstance(value, dict) else value)
	if isinstance(value, dict):
		for k, v in value.items():
			print('    ' + k, '->', v)
