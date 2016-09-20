#!/usr/bin/env python3

def add(x, y):
    yield x + y

def main():
    r = yield add(2, 9)
    print(r)
    yield

if __name__ == '__main__':
    m = main()
    sub = m.send(None)
    result = sub.send(None)
    m.send(result)


