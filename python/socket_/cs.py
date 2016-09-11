#!/usr/bin/env python3

import socket, threading, random, multiprocessing, time

def processClient(client, address):
    print('In', threading.currentThread().name, 'client descriptor', client, 'client address', address)
    client.send(b'    Welcome to this server')
    while True:
        data = client.recv(1024)
        print('-> From', address, 'get data: ', data)
        if not data or data.decode('utf-8') == 'exit':
            client.send(b'    Bye')
            break
        client.send(b'    Hello %s' % (data))
    client.close()


def server(address, port):
    fd = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    if isinstance(address, str) and isinstance(port, int):
        fd.bind((address, port))
    else:
        raise ValueError('params invalid')

    fd.listen(3)
    print('>>> socket listen in %s:%s' % (address, port))

    for index in range(3):
        client, addr = fd.accept()
        print('>>> client connected', '%s/3' % (index))
        t =  threading.Thread(target = processClient, args = (client, addr), name = 'Client Worker')
        t.start()

    print('>>> service end. close socket')
    fd.close()

def client(server):
    if len(server) != 2:
        raise ValueError('params invalid')

    print('Client start', threading.currentThread().name)
    serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serv.connect(server)

    print(' > recv data: ', serv.recv(1024))
    for index in range(random.randint(1, 3)):
        msg = ''
        for chi in range(5):
            msg += chr(random.randint(65, 94))
        serv.send(msg.encode('utf-8'))
        print(' > recv data: ', serv.recv(1024))

    serv.send(b'exit')
    print(' > recv data: ', serv.recv(1024))
    serv.close()

if __name__ == '__main__':
    serv = multiprocessing.Process(target = server, args = ('127.0.0.1', 9527))
    serv.start()

    for index in range(3):
        t = threading.Thread(target = client, args = (('127.0.0.1', 9527),), name = 'User')
        t.start()
        t.join()

    serv.terminate()
