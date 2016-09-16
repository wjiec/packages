#!/usr/bin/env python3

import select, socket

response = b'HTTP/1.1 200 OK\r\nConnection: close\r\n\r\nHello World!'

serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serv.bind(('localhost', 9000))
serv.listen(16)
serv.setblocking(0)

rlist = [ serv ]

while True:
    rl, wl, el = select.select(rlist, [], [])

    for sock in rl:
        if sock == serv:
            client, address = serv.accept()
            rlist.append(client)
            print('[SERVER] Client Connected %s:%s' % address )
        else:
            data = sock.recv(1024)
            if data:
                sock.send(response)
                rlist.remove(sock)
                sock.close()
