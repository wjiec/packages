#!/usr/bin/env python3

from multiprocessing import Process, Pool
import os, time

def proc(name):
    print(time.asctime(), 'child process(name: %s) id %s. ppid %s' % (name, os.getpid(), os.getppid()))
    time.sleep(3)
    print(time.asctime(), 'child process end')

if __name__ == '__main__':
    p = Process(target = proc, args = ('child',))
    
    print(time.asctime(), 'child process will start')
    p.start()
    p.join()
    print('first child process end')

    pl = Pool(4)
    for index in range(4):
    	pl.apply_async(proc, args = (index,))

    pl.close()
    pl.join()
    print(time.asctime(), 'parent process end')
