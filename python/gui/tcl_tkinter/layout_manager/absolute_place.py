#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageTk

class AbsolutePlace(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.style = Style()
        self.parent = parent

        self.__init_window_ui()

    def __init_window_ui(self):
        self.parent.title('Absolute Positioning')
        self.pack(fill = BOTH, expand = 1)

        self.__init_window_style()
        self.__init_window_position(640, 480)

        self.__image_load()

    def __init_window_style(self):
        self.style.configure("TFrame", background = '#333')

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

    def __image_load(self):
        image_1 = Image.open('1.png')
        tk_image_1 = ImageTk.PhotoImage(image_1)
        label_1 = Label(self, image = tk_image_1)
        label_1.image = tk_image_1 # 
        label_1.place(x = 20, y = 20)

        image_2 = Image.open('2.png')
        tk_image_2 = ImageTk.PhotoImage(image_2)
        label_2 = Label(self, image = tk_image_2)
        label_2.image = tk_image_2
        label_2.place(x = 150, y = 150)

        image_3 = Image.open('3.png')
        tk_image_3 = ImageTk.PhotoImage(image_3)
        label_3 = Label(self, image = tk_image_3)
        label_3.image = tk_image_3
        label_3.place(x = 300, y = 300)

        label_4 = Label(self, text = 'Name')
        label_4.place(x = 0, y = 400)

        entry_var = StringVar(self)
        entry_1 = Entry(self, textvariable = entry_var)
        entry_1.place(x = 60, y = 400)

        self.entry = entry_var

        button_1 = Button(self, text = 'OK', command = self.__print)
        button_1.place(x = 210, y = 400)

    def __print(self):
        print(self.entry.get())
        self.entry.set('Hello World')



if __name__ == '__main__':
    root = Tk()
    main_frame = AbsolutePlace(root)
    root.mainloop()