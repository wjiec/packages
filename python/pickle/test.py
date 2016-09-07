#!/usr/bin/env python3
import pickle

class Apple(object):
    
    def __init(self):
        self.__name = 'Apple'
        self.__nick = 'AAAAA'

    def __str__(self):
        return 'This is an Apple Object'

if __name__ == '__main__':
    t = (1, 'str', True, Apple(), Apple())
    l = [1, 2, 3, 4, 5, 'str']
    d = { 'a': 1, 'b': 2, 'c': 3 }
    o = Apple()

    with open('dumps.pkl', 'wb') as f:
        pickle.dump(t, f, 2)
        pickle.dump(l, f, 2)
        pickle.dump(d, f, 2)
        pickle.dump(o, f, 2)

    with open('dumps.pkl', 'rb') as f:
        try:
            print(pickle.load(f))
            print(pickle.load(f))
            print(pickle.load(f))
            print(pickle.load(f))
            print(pickle.load(f))
        except EOFError as e:
            print(e)

