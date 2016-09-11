#!/usr/bin/env python3

from PIL import Image, ImageFilter, ImageDraw, ImageFont
import random, platform

def randomChar():
    return chr(random.randint(65, 90))

def randomColor():
    return (random.randint(64, 255), random.randint(64, 255), random.randint(64, 255))

def randomFontClolor():
    return (random.randint(32, 127), random.randint(32, 127), random.randint(32, 127))

w, h = (240, 60)
fontPath = 'C:/Windows/Fonts/Arial.ttf' if platform.system() is 'Windows' else '/Library/Fonts/Arial.ttf' if platform.system() is 'Darwin' else '/usr/share/fonts/Arial.ttf'
im = Image.new('RGB', (w, h), (225, 225, 225))
font = ImageFont.truetype(fontPath, 36)
draw = ImageDraw.Draw(im)

for x in range(w):
    for y in range(h):
        draw.point((x, y), fill = randomColor())

for word in range(4):
    draw.text((60 * word + 10, 10), randomChar(), font = font, fill = randomFontClolor())

im = im.filter(ImageFilter.BLUR)

im.save('verification_code.jpg', 'jpeg')

print(im.tobytes())