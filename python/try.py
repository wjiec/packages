#!/bin/env python3

try:
    invalid = 10 / 0
except ZeroDivisionError as e:
    print('catch except: ', e)
finally:
    print('finally...')

try:
    invalid = 10 / 1
except ZeroDivisionError as e:
    print('catch except: ', e)
finally:
    print('finally...')