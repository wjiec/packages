#!/usr/bin/python

import requests

response = requests.get('http://docs.python-requests.org/zh_CN/latest/_static/requests-sidebar.png', stream = True)

print(response.raw)
print(response.raw.read(16))
with open('raw.dat', 'wb') as f:
    for chunk in response.iter_content(128):
        f.write(chunk)
