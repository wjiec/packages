#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *

class WidgetScale(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Widget Scale')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(300, 300)
        self.__init_scale()

    def __init_scale(self):
        scale = Scale(self, from_ = 0, to = 100, command = self.on_scale)
        scale.pack(fill = X, padx = 10, pady = 5)

        self.scale_val = IntVar()
        label = Label(self, text = 0, textvariable = self.scale_val)
        label.pack()

    def on_scale(self, val):
        val = int(float(val))
        self.scale_val.set(val)


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
    main_frame = WidgetScale(root)

    root.mainloop()
