#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *
from PIL import Image, ImageTk

class PackReview(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.style = Style()
        self.parent = parent

        self.__init_window_ui()

    def __init_window_ui(self):
        self.parent.title('Pack - Review')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_style()
        self.__init_window_position(300, 480)

        self.__widget_button()

    def __init_window_style(self):
        self.style.theme_use('default')
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

    def __widget_button(self):
        frame_top = Frame(self)
        frame_top.pack(fill = X)

        top_label = Label(frame_top, text = 'Title: ', width = 10)
        top_label.pack(side = LEFT, padx = 5, pady = 5)

        top_entry = Entry(frame_top)
        top_entry.pack(fill = X, padx = 5, pady = 5, expand = True)

        frame_mid = Frame(self)
        frame_mid.pack(fill = X)

        mid_label = Label(frame_mid, text = 'Author: ', width = 10)
        mid_label.pack(side = LEFT, padx = 5, pady = 5)

        mid_entry = Entry(frame_mid)
        mid_entry.pack(fill = X, padx = 5, pady = 5, expand = True)

        frame_bot = Frame(self)
        frame_bot.pack(fill = X)

        bot_label = Label(frame_bot, text = 'Text: ', width = 10)
        bot_label.pack(side = LEFT, anchor = N, padx = 5, pady = 5)

        bot_text = Text(frame_bot)
        bot_text.pack(fill = X, padx = 5, pady = 5, expand = True)

        frame_submit = Frame(self)
        frame_submit.pack(fill = X, side = BOTTOM)

        submit_buttton_cancel = Button(frame_submit, text = 'Cancel')
        submit_buttton_cancel.pack(side = RIGHT, padx = 5, pady = 5)

        submit_buttton_ok = Button(frame_submit, text = 'Submit', command = self.__print)
        submit_buttton_ok.pack(side = RIGHT, padx = 5, pady = 5)
        

    def __print():
        pass

if __name__ == '__main__':
    root = Tk()
    main_frame = PackReview(root)
    root.mainloop()