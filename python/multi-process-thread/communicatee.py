#!/usr/bin/env python 3

from multiprocessing import Process, Queue
import time, os, random

def writeToQueue(queue):
    print(time.asctime(), 'Child Process(Write): %s %s' % (os.getpid(), os.getppid()))
    for value in [ 'S', 'H', 'A', 'D', 'O', 'W' ]:
        queue.put(value)
        t = random.random()
        print('    Child Process(Write) Sleep, %lfs' % (t))
        time.sleep(t)

def readFromQueue(queue):
    print(time.asctime(), 'Child Process(Read): %s %s' % (os.getpid(), os.getppid()))

    while True:
        value = queue.get(True)
        print('  From Queue Getting Data, %s', value)

if __name__ == '__main__':
    queue = Queue()

    print('Create Child Process, Will Start')
    pw = Process(target = writeToQueue, args = (queue,))
    pr = Process(target = readFromQueue, args = (queue,))

    pw.start()
    pr.start()

    print('Waiting Child Process...')
    pw.join()
    pr.terminate()

    print('Parent end')
