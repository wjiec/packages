#!/usr/bin/python35

s1 = '12345'
s2 = 'abcde'

print('s1 = %s, id(s1) = %d' %(s1, id(s1)))
print('s2 = %s, id(s2) = %d' %(s2, id(s2)))

s2 = '12345'

print('')
print('s1 = %s, id(s1) = %d' %(s1, id(s1)))
print('s2 = %s, id(s2) = %d' %(s2, id(s2)))

