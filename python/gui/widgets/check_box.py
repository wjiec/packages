#!/usr/bin/env python3
from tkinter import *

class CenteringWindow(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Widget Checkbutton')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(300, 300)
        self.__init_check_btn()

    def __init_check_btn(self):
        self.check_btn_var = BooleanVar()
        checkbtn = Checkbutton(self, text = 'Show title',
            variable = self.check_btn_var, command = self.on_click)
        checkbtn.select()
        checkbtn.place(x = 100, y = 100)

    def on_click(self):
        if self.check_btn_var.get():
            self.master.title('Widget Checkbutton')
        else:
            self.master.title('')

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
    main_frame = CenteringWindow(root)

    root.mainloop()
