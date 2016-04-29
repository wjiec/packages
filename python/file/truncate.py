#!/usr/bin/python35

fp = open('hello.txt', 'r+')
print('%s contents => \n%s' %(fp.name, fp.read()))

fp.seek(0, 0)
fp.truncate()

print('%s contents => \n%s' %(fp.name, fp.read()))

fp.close()
