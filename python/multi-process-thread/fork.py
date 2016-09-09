#!/usr/bin/env python3

import os

print('os.getpid', os.getpid())
print('os.getpid', os.getppid())

if 'fork' not in dir(os):
	print('unsupport os.fork')
else:
	pid = os.fork()
	if pid is 0:
		print('I\'m child process, pid %s, ppid %s' % (os.getpid(), os.getppid()))
	else:
		print('I\'m parent process, create child pid %s, ppid %s' % (pid, os.getppid()))
