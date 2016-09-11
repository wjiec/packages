#!/usr/bin/env python3

from PIL import Image

im = Image.open('cat.jpg')

print('im type is', im)
print('image size', im.size)

w, h = im.size
im.thumbnail((w // 2, h // 3))
print('resize image to', im.size)
im.save('thumbnail.jpg')