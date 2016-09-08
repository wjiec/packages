#!/usr/bin/env python3

import requests

response = requests.get('http://httbin.org/get', stream = True)

print(response.headers)
