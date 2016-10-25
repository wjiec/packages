#!/usr/bin/env python3

import random

def conpon(length, count):
    result = []
    for i in range(abs(count)):
        cp = ''
        for l in range(abs(length)):
            t = random.randint(0, 2)
            ch = ''
            if t == 0: # number
                while ch in cp:
                    ch = chr(random.randint(48, 57))
            elif t == 1:
                while ch in cp:
                    ch = chr(random.randint(65, 90))
            else:
                while ch in cp:
                    ch = chr(random.randint(97, 122))
            cp += ch
        result.append(cp)

    return result

print(conpon(16, 200))
