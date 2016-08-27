#!/usr/bin/python3

def args(name, *args, **kwords):
    print(name, args, kwords)

def args1(name, *args, count, size):
    print(name, args, count, size)

if __name__ == '__main__':
    args('apple', 123, True, 'ABC', ('1', '2'), count = 99, size = 16);

    try:
        args1('apple', count = 99, size = 16)
        args1('apple', iCount = 99)
    except TypeError:
        print('catch exception')
