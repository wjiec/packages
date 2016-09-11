#!/usr/bin/env python3

import socket

fd = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

fd.connect(('www.baidu.com', 80))

fd.send(b'GET / HTTP/1.1\r\nHost: www.baidu.com\r\nConnection: close\r\n\r\n')

recvBuffer = []
while True:
    d = fd.recv(1024)
    if d:
        recvBuffer.append(d)
    else:
        break

print(b''.join(recvBuffer).decode('utf-8'))

with open('baidu.html', 'wb') as f:
    f.write(b''.join(recvBuffer))
