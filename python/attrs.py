#!/usr/bin/env python3

class Apple(object):
	__attrs__ = [ 'prints' ]

	def __init__(self):
		print('In Apple __init__()')

	def prints(self):
		return 'In Apple prints()'

	def printf(self):
		return 'In Apple printf()'


if __name__ == '__main__':
	apple = Apple()
	print(apple.prints(), apple.printf(), apple.printf)
