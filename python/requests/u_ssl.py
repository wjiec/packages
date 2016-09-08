#!/usr/bin/env python3

import requests

try:
    requests.get('https://github.com', verify = True)
    requests.get('https://kennethreitz.com', verify = False)
    requests.get('https://kennethreitz.com', verify = True)
except Exception as e:
    print('exception: ', e)