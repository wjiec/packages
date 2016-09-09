#!/usr/bin/env python3

import os

print('os.name =', os.name)
print('os.uname() =', 'None' if 'uname' not in dir(os) else os.uname)
print('os.path.abspath(\'.\') =', os.path.abspath('.'))
print('os.path.join ->', os.path.join('etc', 'apache', 'httpd.conf'))

s = os.path.split('/etc/apache/httpd.conf')
print('os.path.split ->', s[0], s[1])

s = os.path.splitext('/etc/apache/httpd.conf')
print('os.path.splitext ->', s[0], s[1])

for f in [ f for f in os.listdir('.') if os.path.isfile(f) and os.path.splitext(f)[1] == '.py' ]:
	print(f, end = ' ')
print('')