#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageTk

class PackManager(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.style = Style()
        self.parent = parent

        self.__init_window_ui()

    def __init_window_ui(self):
        self.parent.title('PackManager')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_style()
        self.__init_window_position(640, 480)

        self.__widget_button()

    def __init_window_style(self):
        self.style.theme_use('default')
        # self.style.configure("TFrame", background = '#333')

    def __init_window_position(self, width = None, height = None):
        screen_width = self.parent.winfo_screenwidth()
        screen_height = self.parent.winfo_screenheight()

        if width is None:
            width = screen_width
        if height is None:
            height = screen_height

        x_coordinate = int((screen_width - width) / 2)
        y_coordinate = int((screen_height - height) / 2)

        self.parent.geometry('{}x{}+{}+{}'.format(width, height, x_coordinate, y_coordinate))

    def __widget_button(self):
        frame = Frame(self, relief = RAISED, borderwidth = 1)
        frame.pack(fill = BOTH, expand = True)

        close_button = Button(self, text = 'Close')
        close_button.pack(side = RIGHT, padx = 5, pady = 5)

        ok_button = Button(self, text = 'OK')
        ok_button.pack(side = RIGHT)

        frame_button_left = Button(frame, text = 'Left')
        frame_button_left.pack(side = LEFT)

        frame_button_right = Button(frame, text = 'Right')
        frame_button_right.pack(side = RIGHT)

if __name__ == '__main__':
    root = Tk()
    main_frame = PackManager(root)
    root.mainloop()