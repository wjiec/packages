#!/usr/bin/env python3
from tkinter import Frame, BOTH, Tk, SUNKEN, Menu, RAISED, FLAT, Button, LEFT, X, TOP, Text, END
import tkinter.ttk as ttk
import tkinter.filedialog as fd
from PIL import Image, ImageTk

class DialogApp(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Dialog')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(640, 480)
        self.__init_file_dialog()

    def __init_file_dialog(self):
        self.__init_menubar()
        self.__init_toolbar()

    def __init_menubar(self):
        self.menubar = Menu(self.master)
        self.master.config(menu = self.menubar)

        file_menu = Menu(self.menubar, tearoff = False)
        file_menu.add_command(label = 'Open', underline = 0, command = self.open_file)
        self.menubar.add_cascade(label = 'File', menu = file_menu, underline = 0)

    def __init_toolbar(self):
        self.toolbar = Frame(self, relief = RAISED, border = 1)
        self.toolbar.pack(fill = X, side = TOP)

        image = Image.open('open.png')
        image = image.resize((24, 24), Image.ANTIALIAS)
        image_tk = ImageTk.PhotoImage(image)
        btn_open_file = Button(self.toolbar, image = image_tk, relief = FLAT, command = self.open_file)
        btn_open_file.image = image_tk
        btn_open_file.pack(side = LEFT, padx = 1, pady = 2)

        self.text = Text(self, relief = SUNKEN, border = 1)
        self.text.pack(fill = BOTH, expand = True, padx = 5, pady = 5)

    def open_file(self):
        file_types = [
            ('Python files', '*.py'),
            ('All files', '*')
        ]

        file_dialog = fd.Open(self, filetypes = file_types)
        path_to_file = file_dialog.show()

        if path_to_file is not '':
            contents = self.read_file(path_to_file)
            self.text.insert(END, contents)

    def read_file(self, path_to_file):
        with open(path_to_file, 'r') as f:
            return f.read()


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
