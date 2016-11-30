#!/usr/bin/env python3
from tkinter import Frame, BOTH, Tk, SUNKEN
from tkinter.ttk import Button
import tkinter.colorchooser as cc

class DialogApp(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('color chooser')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(640, 480)
        self.__init_color_chooser()

    def __init_color_chooser(self):
        btn_color_chooser = Button(self, text = 'choose color', command = self.on_choose)
        btn_color_chooser.pack(pady = 20)

        self.frame = Frame(self, border = 1, relief = SUNKEN, width = 100, height = 100)
        self.frame.pack()

    def on_choose(self):
        (rgb, hx) = cc.askcolor()
        self.frame.config(background = hx)

    def __init_window_position(self, width = None, height = None):
        screen_width = self.master.winfo_screenwidth()
        screen_height = self.master.winfo_screenheight()

        if width is None:
            width = screen_width
        if height is None:
            height = screen_height

        x_coordinate = int((screen_width - width) / 2)
        y_coordinate = int((screen_height - height) / 2)

        self.master.geometry('{}x{}+{}+{}'.format(width, height, x_coordinate, y_coordinate))

if __name__ == '__main__':
    root = Tk()
    main_frame = DialogApp(root)

    root.mainloop()
