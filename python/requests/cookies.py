#!/usr/bin/env python3

import requests

url = 'https://google.com'
response = requests.get(url)
print(response.cookies)

cookies = dict(cookieName = 'cookieValue')
response = requests.get('http://httpbin.org/cookies', cookies = cookies)
print(response.text)