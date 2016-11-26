#!/usr/bin/env python3
from tkinter import *

class CenteringWindow(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)
        self.parent = parent

        self.__init_window_ui()

    def __init_window_ui(self):
        self.pack(fill = BOTH, expand = 1)

        self.parent.title('Centering Window')

        self.__init_window_position(1366, 768)

    def __init_window_position(self, width = None, height = None):
        screen_width = self.parent.winfo_screenwidth()
        screen_height = self.parent.winfo_screenheight()

        if width is None:
            width = screen_width
        if height is None:
            height = screen_height

        x_coorindate = int((screen_width - width) / 2)
        y_coordinate = int((screen_height - height) / 2)

        self.parent.geometry('{}x{}+{}+{}'.format(width, height, x_coorindate, y_coordinate))

if __name__ == '__main__':
    root = Tk()
    main_frame = CenteringWindow(root)

    root.mainloop()
