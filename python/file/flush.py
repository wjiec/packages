#!/usr/bin/python35

fp1 = open('hello.txt', 'a')
fp1.write('append to this file by flush.sh')
fp1.flush()

fp2 = open('hello.txt')
print('fp2.read() = %s' % (fp2.read()))

fp1.close()
fp2.close()

