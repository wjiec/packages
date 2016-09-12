#!/usr/bin/env python3

from flask import Flask, request

application = Flask(__name__)

@application.route('/', methods = [ 'GET' ])
def index():
    return 'Hello World'

if __name__ == '__main__':
    application.run()