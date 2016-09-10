#!/usr/bin/env python3

import itertools

for n in itertools.count(1):
    if n is 10:
        break
    print(n, end = ' ')

c = 0
for n in itertools.cycle('ABC'):
    if n is 'C' and c is 10:
        break
    if n is 'C':
        c += 1
        print('\t')
    print(n, end = ' ')
print()

for n in itertools.repeat('S', 9):
    print(n, end = ' ')
print()

print(list(itertools.takewhile(lambda n: n < 10, itertools.count(1))))

print(list(itertools.chain('ABC', 'DEF')))

print(dict(itertools.groupby('ABSAAHHSSWEE')))
