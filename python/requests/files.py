#!/usr/bin/python

import requests, json

fd = open('header.py', 'rb')

response = requests.post('http://httpbin.org/post', files = { 'file': fd })
print(response.json().get('files').get('file'))

files = { 'file': ('header.py', fd, 'text/plain', { 'Expires': '0' }) }

response = requests.post('http://httpbin.org/post', files = files)
print(response.json())