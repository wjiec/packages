#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from __future__ import print_function
import cv2
import numpy as np
from matplotlib import pyplot as plt

img = cv2.imread('../opencv-logo.png', cv2.IMREAD_UNCHANGED)

plt.imshow(img, cmap='gray', interpolation='bicubic')
plt.xticks([]), plt.yticks([])
plt.show()

x = np.arange(0, 5, 0.1);
y = np.sin(x)
plt.plot(x, y)