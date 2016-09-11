#!/usr/bin/env python3

try:
    from tkinter import *
except ImportError:
    try:
        import tkinter
    except ImportError:
        print('tkinter module not found')
        exit()

