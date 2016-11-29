#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageTk

class CenteringWindow(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Widget Scale')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(640, 480)
        self.__init_toolbar()

    def __init_toolbar(self):
        self.menubar = Menu(self.master)
        self.master.config(menu = self.menubar)

        file_menu = Menu(self.menubar, tearoff = False)
        file_menu.add_command(label = 'Exit', underline = 0, command = self.quit)

        self.menubar.add_cascade(label = 'File', menu = file_menu)

        toolbar = Frame(self, relief = RAISED)

        image = Image.open('quit.png')
        image_tk = ImageTk.PhotoImage(image)
        btn_exit = Button(toolbar, image = image_tk, command = self.quit)
        btn_exit.image = image_tk
        btn_exit.pack(side = LEFT, padx = 2, pady = 3)

        toolbar.pack(side = TOP, fill = X)

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
