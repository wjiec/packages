#!/usr/bin/python3

def triangles(lineCount):
    if not isinstance(lineCount, int):
        raise TypeError('invalid params')

    line = [ 1 ]
    for l in range(lineCount):
        yield line
        line = [ 1 ] + [ line[i] + line[i + 1] for i in range(len(line) - 1)  ] + [ 1 ]
    return

if __name__ == '__main__':
    for line in triangles(10):
        print(line)
