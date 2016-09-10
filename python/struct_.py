#!/usr/bin/env python3

import struct

print(struct.pack('>h', 0x0102)) # big-ending AX -> HA, LA
print(struct.pack('<h', 0x0201)) # little-ending

bmp = b'\x42\x4d\x38\x8c\x0a\x00\x00\x00\x00\x00\x36\x00\x00\x00\x28\x00\x00\x00\x80\x02\x00\x00\x68\x01\x00\x00\x01\x00\x18\x00'
print(struct.unpack('<ccIIIIIIhh', bmp))