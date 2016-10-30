#!/usr/bin/env python3
''' Line Counter
'''

import os
import sys
import argparse
from collections import namedtuple

__version__ = (1, 0, 0)
__author__  = 'ShadowMan(Wang)'

''' VERBOSE

output verbosity

'''
VERBOSE = 0

''' Usage
usage: liveCounter.py [-h] [-i INCLUDE] [-e EXCLUDE] [-v | -q] directory

positional arguments:
  directory             need to count the number of rows of the list

optional arguments:
  -h, --help            show this help message and exit
  -i INCLUDE, --include INCLUDE
                        a comma-separated list of extensions to be included
  -e EXCLUDE, --exclude EXCLUDE
                        a comma-separated list of extensions to be excluded
  -v, --verbose         increase output verbosity
  -q, --quiet           decrease output verbosity
'''
parse = argparse.ArgumentParser()
group = parse.add_mutually_exclusive_group()
parse.add_argument('-i', '--include', help = 'a comma-separated list of extensions to be included')
parse.add_argument('-e', '--exclude', help = 'a comma-separated list of extensions to be excluded')
parse.add_argument('-d', '--depth', help = 'traversing file depth')
group.add_argument('-v', '--verbose', help = 'increase output verbosity', action = 'count', default = 0)
group.add_argument('-q', '--quiet', help = 'decrease output verbosity', action = 'store_true')
parse.add_argument('directory', help = 'need to count the number of rows of the list', type = str)

''' Example

$ python lineCounter.py -ipy,md -epyc src

'''
args = parse.parse_args()

if args.verbose >= 2:
    VERBOSE = 2
elif args.verbose == 1:
    VERBOSE = 1
else:
    VERBOSE = 0

if VERBOSE >= 1:
    print('[VERBOSE] output verbosity = {}'.format(VERBOSE))

''' Include extensions

Example:
    -ihtml,css,js
    --include=html,css,js

'''
if args.include is not None:
    INCLUDE = list(filter(lambda el: el.isalpha(), map(lambda el: el.strip('.'), args.include.split(','))))
    if VERBOSE >= 1:
        print('[INCLUDE]', INCLUDE)
else:
    INCLUDE = []

''' Exclude extensions

Example:
    -epyc,swp
    --exclude=pyc,swp

'''
if args.exclude is not None:
    EXCLUDE = list(filter(lambda el: el.isalpha(), map(lambda el: el.strip('.'), args.exclude.split(','))))
    if VERBOSE >= 1:
        print('[EXCLUDE]', EXCLUDE)
else:
    EXCLUDE = []

''' Directory

check directory available

'''
if not os.path.isdir(args.directory):
    if VERBOSE >= 1:
        print('[DIRECTORY] {} is not directory'.format(args.directory))
    else:
        print('\'{}\' is not directory'.format(args.directory))
    exit()

''' Result

Result Format

'''
Result = namedtuple('Result', 'source blank comment')

def ccpp(lines):
    pass

def python(lines):
    pass

def php(lines):
    pass

parser = {
    'c': ccpp,
    'cpp': ccpp,
    'php': php,
    'python': python
}

def counter(directory):
    global INCLUDE
    global EXCLUDE
    for parent, dirnames, filenames in os.walk(directory):
        print('\n', parent)
        for filename in filenames:
            name, extension = os.path.splitext(filename)
            extension = extension.strip('.')

counter(args.directory)