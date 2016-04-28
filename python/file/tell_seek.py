#!/usr/bin/python35

fp = open('hello.txt')

print('fp.tell = %s' % (fp.tell()))

fp.seek(10, SEEK_SET)
print('fp.seek(10), fp.tell() = %s' % (fp.tell()))

# only do zero cur-relative seeks
fp.seek(0, SEEK_CUR)
print('fp.seek(0, 1), fp.tell() = %s' % (fp.tell()))

fp.seek(0, SEEK_END)
print('fp.seek(0, 2), fp.tell() = %s' % (fp.tell()))
