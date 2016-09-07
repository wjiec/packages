#!/usr/bin/python

import requests, json

url = 'http://httpbin.org/post'
payload = { 'key': 'value' }

response = requests.post(url, data = payload)
print(response.json().get('form'))

response = requests.post(url, data = json.dumps(payload))
print(response.json().get('data'))

response = requests.post(url, json = payload)
print(response.json().get('data'))