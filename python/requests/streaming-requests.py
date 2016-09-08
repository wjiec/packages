#!/usr/bin/env python3

import requests, json

response = requests.get('http://httpbin.org/stream/9', stream = True)
for line in response.iter_lines():
    print(json.loads(line.decode('utf-8')))

    ''' bytes <==> str

                decode
        bytes ==========> str

                encode
        str   ==========> bytes

    '''