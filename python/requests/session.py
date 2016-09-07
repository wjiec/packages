#!/usr/bin/env python3

import requests

session = requests.Session()

response = session.get('http://httpbin.org/cookies/set/sessioncookie/123456789')
print(response.text)

response = session.get('http://httpbin.org/cookies')
print(response.text)