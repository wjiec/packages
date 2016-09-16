#!/usr/bin/env python3

import select, socket

response = b'HTTP/1.1 200 OK\r\nConnection: close\r\n\r\nHello World!'

serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serv.bind(('0.0.0.0', 9000))
serv.listen(16)
serv.setblocking(0)

epoll = select.epoll()
epoll.register(serv.fileno(), select.EPOLLIN)

clients = {}
try:
    while True:
        events = epoll.poll()
        for fd, event in events:
            if fd == serv.fileno():
                client, address = serv.accept()
                client.setblocking(0)
                print('[SERVER] Client %s:%s Connected' % address)
                clients[fd] = client
            elif event == select.EPOLLIN:
                client = clients[fd]
                data = client.recv(1024)
                poll.modify(fd, select.EPOLLOUT)
            elif event == select.EPOLLOUT:
                client = clients[fd]
                client.send(response)
            elif event == select.EPOLLHUP:
                epll.unregister(fd)
                client.close()
                del clients[fd]

    serv.close()
except KeyboardInterrupt:
    serv.close()
    for client in clisnts:
        clients.close()

