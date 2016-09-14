#!/usr/bin/env python3

def coroutine(func):
    def wrapper(*keys, **kwargs):
        c = func(*keys, **kwargs)
        c.send(None)
        return c
    return wrapper

def tail(thefile, target):
    thefile.seek(0, 2)
    while True:
        line = thefile.readline()
        if not line:
            continue
        target.send(line)
    target.close()

@coroutine
def printer():
    while True:
        try:
            msg = yield
            print(msg)
        except GeneratorExit as e:
            printer(e)


if __name__ == '__main__':
    tail(open('logfile.log', 'r'), printer())

