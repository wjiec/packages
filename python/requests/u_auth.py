#!/usr/bin/env python3

import requests

response = requests.get('http://httpbin.org/get', auth = ( 'username', 'password' ), headers = { 'User-Agent': 'Chrome' })
print(response.url)
print(response.request.headers)