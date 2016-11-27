#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *

class GridPosition(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.style = Style()
        self.parent = parent

        self.__init_window_ui()

    def __init_window_ui(self):
        self.parent.title('Calculator')

        self.__init_window_style()
        # self.__init_window_position(640, 480)

        self.__widget_button()

        self.pack(fill = BOTH, expand = True)

    def __init_window_style(self):
        # self.style.theme_use('default')
        self.style.configure("TButton", padding = (0, 5, 0, 5), font = 'Courier 10')

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
        self.columnconfigure(0, pad = 3)
        self.columnconfigure(1, pad = 3)
        self.columnconfigure(2, pad = 3)
        self.columnconfigure(3, pad = 3)

        self.rowconfigure(0, pad = 3)
        self.rowconfigure(1, pad = 3)
        self.rowconfigure(2, pad = 3)
        self.rowconfigure(3, pad = 3)
        self.rowconfigure(4, pad = 3)

        entry = Entry(self)
        entry.grid(row = 0, columnspan = 4, sticky = W + E)

        btn_cls = Button(self, text = 'Cls')
        btn_cls.grid(row = 1, column = 0)

        btn_back = Button(self, text = 'Back')
        btn_back.grid(row = 1, column = 1)

        btn_blank = Button(self, text = '')
        btn_blank.grid(row = 1, column = 2)

        btn_close = Button(self, text = 'Close')
        btn_close.grid(row = 1, column = 3)

        btn_num_7 = Button(self, text = '7')
        btn_num_7.grid(row = 2, column = 0)

        btn_num_8 = Button(self, text = '8')
        btn_num_8.grid(row = 2, column = 1)

        btn_num_9 = Button(self, text = '9')
        btn_num_9.grid(row = 2, column = 2)

        btn_div = Button(self, text = '/')
        btn_div.grid(row = 2, column = 3)

        btn_num_4 = Button(self, text = '4')
        btn_num_4.grid(row = 3, column = 0)

        btn_num_5 = Button(self, text = '5')
        btn_num_5.grid(row = 3, column = 1)

        btn_num_6 = Button(self, text = '6')
        btn_num_6.grid(row = 3, column = 2)

        btn_mul = Button(self, text = '*')
        btn_mul.grid(row = 3, column = 3)

        btn_num_1 = Button(self, text = '1')
        btn_num_1.grid(row = 4, column = 0)

        btn_num_2 = Button(self, text = '2')
        btn_num_2.grid(row = 4, column = 1)

        btn_num_3 = Button(self, text = '3')
        btn_num_3.grid(row = 4, column = 2)

        btn_dec = Button(self, text = '-')
        btn_dec.grid(row = 4, column = 3)

        btn_num_0 = Button(self, text = '0')
        btn_num_0.grid(row = 5, column = 0)

        btn_dot = Button(self, text = '.')
        btn_dot.grid(row = 5, column = 1)

        btn_equal = Button(self, text = '=')
        btn_equal.grid(row = 5, column = 2)

        btn_add = Button(self, text = '+')
        btn_add.grid(row = 5, column = 3)


if __name__ == '__main__':
    root = Tk()
    main_frame = GridPosition(root)
    root.mainloop()