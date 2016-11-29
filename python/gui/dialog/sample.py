#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *
import tkinter.messagebox as MB

class DialogApp(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Dialog')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(640, 480)
        self.__init_dailog()

    def __init_dailog(self):
        btn_error = Button(self, text = 'Show Error', command = self.show_error)
        btn_error.grid(padx = 5, pady = 5)

        btn_warning = Button(self, text = 'Show Warning', command = self.show_warning)
        btn_warning.grid(row = 0, column = 1, padx = 5, pady = 5)

        btn_question = Button(self, text = 'Show Question', command = self.show_question)
        btn_question.grid(row = 0, column = 3, padx = 5, pady = 5)

        btn_information = Button(self, text = 'Show Information', command = self.show_information)
        btn_information.grid(row = 0, column = 4, padx = 5, pady = 5)

    def show_error(self):
        MB.showerror('Error', 'This is a error dialog')

    def show_warning(self):
        MB.showwarning('Warning', 'this is a warning dialog')

    def show_question(self):
        MB.askquestion('Question', 'are you sure exit')

    def show_information(self):
        MB.showinfo('Information', 'this is a dialog')

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
