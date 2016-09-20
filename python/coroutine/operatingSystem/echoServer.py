#!/usr/bin/env python3

import scheduler
from systemCall import *
import threading

import socket, time, random

def handleClient(client, address):
    print('>>> client connect[%s:%s]' % address)
    while True:
        data = yield sockRecv(client, 1024)
        if not data:
            break
        print('---', data)
        yield sockSend(client, b'HTTP/1.1 200 OK\r\nConnection: close\r\n\r\nHello World!')
    client.close()


def server(address, port):
    serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serv.bind((address, port))
    serv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    serv.listen(8)
    print('server start in %s:%s' % (address, port))
    while True:
        client, address = sockAccept(serv)
        yield CreateTask(handleClient(client, address))

def Client(address, port):
    s = random.randint(1, 5)
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
    scheduler.mainLoop()