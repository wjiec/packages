#!/usr/bin/env python3
import tkinter as tk
import tkinter.ttk as ttk

class CanvasDraw(tk.Frame):

    def __init__(self, parent):
        super(CanvasDraw, self).__init__(parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Canvas Draw Colours')
        self.pack(fill = tk.BOTH, expand = True)
        self.style = ttk.Style()

        self.style.configure("TFrame", background = '#333')

        # self.__init_window_size(640, 480)
        self.__init_canvas()

    def __init_canvas(self):
        canvas = tk.Canvas(self)
        
        canvas.create_rectangle(30, 10, 120, 80, outline="#fb0", fill="#fb0")
        canvas.create_rectangle(150, 10, 240, 80, outline="#f50", fill="#f50")
        canvas.create_rectangle(270, 10, 370, 80, outline="#05f", fill="#05f")

        canvas.pack(fill = tk.BOTH, expand = True)

    def __init_window_size(self, width = None, height = None):
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
    root = tk.Tk()
    main_frame = CanvasDraw(root)

    root.mainloop()

