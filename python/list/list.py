#!/usr/bin/python35

apple = ['a', 'p', 'p', 'a']
print('apple = ', apple, '\ttype(apple) = ', type(apple), '\tid(apple) = ', id(apple))

apple[3] = 'l'
print('apple = ', apple, '\ttype(apple) = ', type(apple), '\tid(apple) = ', id(apple))

apple.append('e')
print('apple = ', apple, '\ttype(apple) = ', type(apple), '\tid(apple) = ', id(apple))

apple.append('e')
print('apple = ', apple, '\ttype(apple) = ', type(apple), '\tid(apple) = ', id(apple))

apple.remove('e')
print('apple = ', apple, '\ttype(apple) = ', type(apple), '\tid(apple) = ', id(apple))
