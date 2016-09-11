#!/usr/bin/env python3

import socket, threading, time

def server(server, port):
    print('listening %s:%s' % ( server, port ))

    fd = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    fd.bind((server, port))

    while True:
        data, address = fd.recvfrom(1024)
        print('from', address, 'getting data', data)
        fd.sendto(b'I don\'t know', address)


def client(server, port, message):
    print('connect to %s:%s' % (server, port))

    fd = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    fd.sendto(message.encode('utf-8'), (server, port))
    print('from server recv data: ', fd.recv(1024).decode('utf-8'))

if __name__ == '__main__':
    p = threading.Thread(target = server, args = ('127.0.0.1', 9527))
    p.start()

    time.sleep(.5)
    for msg in [ 'Super', 'High', 'Velocity' ]:
        t = threading.Thread(target = client, args = ('127.0.0.1', 9527, msg))
        t.start()
        t.join()
