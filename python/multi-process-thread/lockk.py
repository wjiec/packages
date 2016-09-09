#!/usr/bin/env python3

import time, random, threading, random

salary = 999

def threadMethod(name):
    print('Thread(%s:%s) Start' % (name, threading.current_thread().name))
    for index in range(999999):
        randomNumber = random.random()
        global salary
        salary = salary + randomNumber
        salary = salary - randomNumber

def threadMethodLock(name, lock):
    print('Thread(%s:%s) Start' % (name, threading.current_thread().name))
    for index in range(999999):
        randomNumber = random.random()

        global salary
        lock.acquire()

        salary = salary + randomNumber
        salary = salary - randomNumber

        lock.release()

if __name__ == '__main__':
    t1 = threading.Thread(target = threadMethod, name = 'threadMethod', args = ('threadMethod',))
    t2 = threading.Thread(target = threadMethod, name = 'threadMethodCopy', args = ('threadMethodCopy',))
    
    t1.start()
    t2.start()
    t1.join()
    t2.join()
    print(time.asctime(), salary)

    lockk = threading.Lock()
    salary = 999
    t3 = threading.Thread(target = threadMethodLock, name = 'threadMethodLock', args = ('threadMethodLock',lockk))
    t4 = threading.Thread(target = threadMethodLock, name = 'threadMethodLockCopy', args = ('threadMethodLockCopy',lockk))
    
    t3.start()
    t4.start()
    t3.join()
    t4.join()
    print(time.asctime(), salary)