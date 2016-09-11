#!/usr/bin/env python3

from PIL import Image, ImageFilter

im = Image.open('cat.jpg')

bim = im.filter(ImageFilter.BLUR)
bim.save('blur.jpg')