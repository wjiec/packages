#!/usr/bin/env python3

import select, socket

response = b'HTTP/1.1 200 OK\r\nConnection: close\r\n\r\nHello World!'

serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serv.bind(('0.0.0.0', 9000))
serv.listen(16)
serv.setblocking(0)

poll = select.poll()
poll.register(serv.fileno(), select.POLLIN)

clients = {}
try:
    while True:
        for fd, event in poll.poll():
            if event == select.POLLIN:
                if fd == serv.fileno():
                    client, address = serv.accept()
                    print('[SERVER] Client %s:%s Connected' % address )
                    poll.register(client.fileno(), select.POLLIN)
                    clients[client.fileno()] = client
                else:
                    client = clients[fd]
                    data = client.recv(1024)
                    if data:
                        poll.modify(fd, select.POLLOUT)
            elif event == select.POLLOUT:
                client = clients[fd]
                client.send(response)
                poll.unregister(fd)
                del clients[fd]
except KeyboardInterrupt:
    serv.close()
    for client in clisnts:
        clients.close()

