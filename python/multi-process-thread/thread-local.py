#!/usr/bin/env python3

import threading

globalLocal = threading.local()

class Student(object):
    def __init__(self, name):
        self.__name = name

    def __str__(self):
        return '<Student Object, name = %s, In %#x>' % ( self.__name, id(self) )

    __repr__ = __str__

def processStudent():
    student = globalLocal.student

    print('    Process Student Function, Object, %s' % student)

def threadMethod(name):
    print('Thread, %s, args, %s' % (threading.currentThread().name, name))
    globalLocal.student = Student(name)
    processStudent()
    print('Thread, %s, exit' % (threading.current_thread().name))

if __name__ == '__main__':
    t1 = threading.Thread(target = threadMethod, args = ('Boy',), name = 'BoyThread')
    t2 = threading.Thread(target = threadMethod, args = ('Girl',), name = 'GirlThread')

    t1.start()
    t2.start()

    t1.join()
    t2.join()