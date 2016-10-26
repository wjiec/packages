#!/usr/bin/env python3

from collections import Counter

def wordsCounter(file):
    counter = Counter()
    with open(file, 'r') as fd:
        for line in fd:
            word = ''
            for ch in line:
                if ch.isalpha() is True:
                    word += ch
                else:
                    if not word == '':
                        counter[word] += 1
                    word = ''

    return counter

print(wordsCounter('coupon.py'))