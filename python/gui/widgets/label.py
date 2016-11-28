#!/usr/bin/env python3
from tkinter import *
from PIL import Image, ImageTk

class CenteringWindow(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Widget Label')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(300, 300)
        self.__init_label()

    def __init_label(self):
        text_label = Label(self, text = 'Text Label')
        text_label.pack()

        image = Image.open('1.png')
        tk_image = ImageTk.PhotoImage(image)

        image_label = Label(self, image = tk_image)
        image_label.image = tk_image
        image_label.pack()

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
