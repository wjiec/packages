#
# coding: utf-8
#
import os

LEFT_HIGH = 1 << 0
LEFT_LOW = 1 << 1
TOP = 1 << 2
RIGHT_HIGH = 1 << 3
RIGHT_LOW = 1 << 4
BOTTOM = 1 << 5
DOT = 1 << 6
MIDDLE = 1 << 7

CHARSET = {
    0x0: (LEFT_LOW | LEFT_HIGH) | (TOP | BOTTOM) | (RIGHT_HIGH | RIGHT_LOW),
    0x1: RIGHT_HIGH | RIGHT_LOW,
    0x2: LEFT_LOW | (TOP | MIDDLE | BOTTOM) | RIGHT_HIGH,
    0x3: (TOP | MIDDLE | BOTTOM) | (RIGHT_HIGH | RIGHT_LOW),
    0x4: LEFT_HIGH | MIDDLE | (RIGHT_HIGH | RIGHT_LOW),
    0x5: LEFT_HIGH | (TOP | MIDDLE | BOTTOM) | RIGHT_LOW,
    0x6: (LEFT_HIGH | LEFT_LOW) | (TOP | MIDDLE | BOTTOM) | RIGHT_LOW,
    0x7: TOP | (RIGHT_HIGH | RIGHT_LOW),
    0x8: (LEFT_HIGH | LEFT_LOW) | (TOP | MIDDLE | BOTTOM) | (RIGHT_HIGH | RIGHT_LOW),
    0x9: LEFT_HIGH | (TOP | MIDDLE | BOTTOM) | (RIGHT_HIGH | RIGHT_LOW),
    0xa: (LEFT_HIGH | LEFT_LOW) | TOP | MIDDLE | (RIGHT_HIGH | RIGHT_LOW),
    0xb: (LEFT_HIGH | LEFT_LOW) | (MIDDLE | BOTTOM) | RIGHT_LOW,
    0xc: (LEFT_HIGH | LEFT_LOW) | (TOP | BOTTOM),
    0xd: LEFT_LOW | (MIDDLE | BOTTOM) | (RIGHT_HIGH | RIGHT_LOW),
    0xe: (LEFT_HIGH | LEFT_LOW) | (TOP | MIDDLE | BOTTOM),
    0xf: (LEFT_HIGH | LEFT_LOW) | (TOP | MIDDLE),
}

if __name__ == '__main__':
    if not os.path.isdir('firmware'):
        os.mkdir('firmware')

    assert len(CHARSET) == 16
    with open('firmware/display.bin', 'wb') as fp:
        for char in CHARSET:
            fp.write(CHARSET[char].to_bytes(1, 'little'))
