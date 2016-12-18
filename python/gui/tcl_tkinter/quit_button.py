#!/usr/bin/env python3
from tkinter import *

class QuitButton(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)
        self.parent = parent

        self.__init_window_ui()

    def __init_window_ui(self):
        self.pack(fill = BOTH, expand = 1)

        self.parent.title('Quit Button')

        self.__init_window_position(1366, 768)
        self.__widget_quit_button()

    def __widget_quit_button(self):
        quit_button = Button(text = 'Quit', command = self.quit)
        quit_button.place(x = 1366 / 2, y = 768 / 2)

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

if __name__ == '__main__':
    root = Tk()
    main_frame = QuitButton(root)

    root.mainloop()
