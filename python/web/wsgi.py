#!/usr/bin/env python3

from wsgiref.simple_server import make_server
def application(environ, start_response):
    print('get request:', environ)
    start_response('200 OK', [('Content-Type', 'text/html')])
    return [('<h1>Hello %s!</h1>' % (environ['USERNAME'])).encode('utf-8'), (environ.__str__().encode('utf-8'))]

if __name__ == '__main__':
    httpd = make_server('0.0.0.0', 9000, application)
    print('http server on port 9000')

    httpd.serve_forever()

