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
        self.parent.title('Dialog')

        self.__init_window_style()
        # self.__init_window_position(640, 480)

        self.__widget_button()

        # self.pack()
        self.pack(fill = BOTH, expand = True)

    def __init_window_style(self):
        self.style.theme_use('alt')
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
        self.columnconfigure(1, weight = True)
        self.columnconfigure(3, pad = 5)

        self.rowconfigure(3, weight = True)
        self.rowconfigure(5, pad = 8)

        label = Label(self, text = 'Windows')
        label.grid(sticky = W, padx = 5, pady = 5)

        text = Text(self)
        text.grid(row = 1, column = 0, columnspan = 2, rowspan = 4, padx = 5, sticky = W + E + N + S)

        btn_activate = Button(self, text = 'Activate')
        btn_activate.grid(row = 1, column = 3, pady = 3)

        btn_close = Button(self, text = 'Close')
        btn_close.grid(row = 2, column = 3, pady = 3)

        btn_help = Button(self, text = 'Help')
        btn_help.grid(row = 5, column = 0, padx = 5)

        btn_ok = Button(self, text = 'OK')
        btn_ok.grid(row = 5, column = 3, padx = 5)

if __name__ == '__main__':
    root = Tk()
    main_frame = GridPosition(root)
    root.mainloop()