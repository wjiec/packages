#!/usr/bin/env python3
from tkinter import *
from tkinter.ttk import *

class CenteringWindow(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Widget Scale')
        self.pack(fill = BOTH, expand = True)

        self.__init_window_position(300, 300)
        self.__init_listbox()

    def __init_listbox(self):
        langs = [ 'C', 'C++', 'Python', 'Ruby', 'PHP' ]

        listbox = Listbox(self)
        for lang in langs:
            listbox.insert(END, lang)
        listbox.bind('<<ListboxSelect>>', self.on_select)
        listbox.pack(pady = 10)

        self.listbox_var = StringVar()
        label = Label(self, text = 'None', textvariable = self.listbox_var)
        label.pack()

    def on_select(self, event):
        sender = event.widget
        current_select = sender.curselection()

        print(sender.get(event.widget.curselection()))

        self.listbox_var.set(sender.get(current_select))


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
