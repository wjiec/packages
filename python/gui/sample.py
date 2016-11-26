#!/usr/bin/env python3
from tkinter import *

class Sample(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent, background = 'white')

        self.parent = parent
        self.__init_window_ui()

    def __init_window_ui(self):
        self.parent.title('Sample')
        self.pack(fill = BOTH, expand = 1)

if __name__ == '__main__':
    root = Tk()
    root.geometry('640x480+300+300')
    frame = Sample(root)
    root.mainloop()