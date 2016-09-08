#!/usr/bin/env python3

import requests

response = requests.get('http://httpbin.org/get', headers = { 'user-agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36' })

print(response.request.headers.get('user-agent'))