#!/usr/bin/env python3

import requests

url = 'http://google.com'
response = requests.get(url)

for history in response.history:
    print(history.url, history.headers, sep = '\n', end = '\n\n')

url = 'http://google.com'
response = requests.head(url, allow_redirects = False)

for history in response.history:
    print(history.url, history.headers, sep = '\n', end = '\n\n')