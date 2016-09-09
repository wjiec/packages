#!/usr/bin/env python3

import time, random, threading

def threadMethod(name):
    print('Thread(%s:%s) Start' % (name, threading.current_thread().name))
    for index in range(9):
        print('    Thread(%s), print, %d' % (name, index))
        st = random.random()
        print('    Thread(%s), sleep, %lfs' % (name, st))
        time.sleep(st)

if __name__ == '__main__':
    t = threading.Thread(target = threadMethod, name = 'threadMethod', args = ('threadMethod',))
    t.start()
    t.join()