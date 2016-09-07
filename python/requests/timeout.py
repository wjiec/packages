#!/usr/bin/env python3

import requests

try:
	url = 'http://google.com'
	response = requests.get(url, timeout = 0.001) # 1 microsec
except Exception as e:
	print(e)