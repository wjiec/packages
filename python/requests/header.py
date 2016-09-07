#!/usr/bin/python

import requests

headers = { 'user-agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36' }

response = requests.get('http://httpbin.org/get', headers = headers)
print(response.headers.get('Date'))