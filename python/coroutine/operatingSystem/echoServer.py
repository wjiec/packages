#!/usr/bin/env python3

import scheduler
from systemCall import *
import threading

import socket, time, random

def handleClient(client, address):
    print('>>> client connect[%s:%s]' % address)
    while True:
        yield WaitRead(client)
        data = client.recv(1024)
        if not data:
            break
        yield WaitWrite(client)
        client.send(data)
    client.close()
    print('>>> client closed[%s:%s]' % address)
    yield


def server(address, port):
    serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serv.bind((address, port))
    serv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    serv.listen(8)
    print('server start in %s:%s' % (address, port))
    while True:
        yield WaitRead(serv)
        client, address = serv.accept()
        yield CreateTask(handleClient(client, address))

def alive():
    while True:
        print('alive')
        yield

def Client(address, port):
    s = random.randint(1, 9)
    print('[Client Thread = %s]' % (threading.currentThread().name), 'sleep %ss' % ( s ))
    time.sleep(s)
    serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serv.connect((address, port))
    serv.send(('Hello World! %s' % ( threading.currentThread().name )).encode('utf-8'))
    print('[Client Thread = %s]' % (threading.currentThread().name), serv.recv(1024))


if __name__ == '__main__':
    for t in range(9):
        threading.Thread(target = Client, args = ('localhost', 9000)).start()

    scheduler = scheduler.Scheduler()
    scheduler.new(server('localhost', 9000))
    # scheduler.new(alive())
    scheduler.mainLoop()