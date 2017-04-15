#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from __future__ import print_function
import cv2

_image_with_color = cv2.imread('../opencv-logo.png', cv2.IMREAD_COLOR)
_image_with_gray = cv2.imread('../opencv-logo.png', cv2.IMREAD_GRAYSCALE)
_image_with_unchanged = cv2.imread('../opencv-logo.png', cv2.IMREAD_UNCHANGED)

cv2.imshow('image with color', _image_with_color)
cv2.waitKey(0)
cv2.destroyAllWindows()

cv2.imshow('image with gray scale', _image_with_gray)
cv2.waitKey(0)
cv2.destroyAllWindows()

cv2.imshow('image with unchanged', _image_with_unchanged)
cv2.waitKey(0)
cv2.destroyAllWindows()

cv2.imshow('window name', _image_with_color)
cv2.destroyWindow('window name')

cv2.imwrite('_image_with_color.png', _image_with_color)
cv2.imwrite('_image_with_gray.png', _image_with_gray)
cv2.imwrite('_image_with_unchanged.png', _image_with_unchanged)

cv2.imshow('image with unchanged', _image_with_unchanged)
k = cv2.waitKey(0)
if k == 27:
    cv2.destroyAllWindows()
elif k == ord('s'):
    cv2.imwrite('_s_image.png', _image_with_unchanged)
    cv2.destroyAllWindows()