#!/usr/bin/python3

import requests

for method in [ 'get', 'post', 'put', 'delete', 'head', 'options' ]:
    print(getattr(requests, method)('http://httpbin.org/' + 
        (method if method is 'post' or method is 'put' or method is 'delete' else 'get')))

