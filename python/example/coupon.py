#!/usr/bin/env python3

import random

def conpon(length, count):
    result = []
    charset = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
    for i in range(abs(count)):
        cp = ''
        for l in range(abs(length)):
            ch = ''
            while ch in cp:
                ch = random.choice(charset)
            cp += ch
        result.append(cp)

    return result

print(conpon(16, 200))
