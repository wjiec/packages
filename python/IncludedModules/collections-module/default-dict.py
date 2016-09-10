#!/usr/bin/env python3

from collections import defaultdict

defaultDict = defaultdict(lambda : None)

defaultDict['key'] = 'value'

for key in ['key', 'invalid']:
	print(key, '->', defaultDict[key])