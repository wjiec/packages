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

        self.__init_window_size(640, 480)
        self.__init_canvas()

    def __init_canvas(self):
        canvas = tk.Canvas(self)
        
        canvas.create_oval(10, 10, 80, 80, outline = 'red', fill = 'green', width = 2)
        canvas.create_oval(110, 10, 210, 80, outline = 'red', fill = 'green', width = 2)
        canvas.create_rectangle(230, 10, 290, 60, outline = 'red', fill = 'green', width = 2)
        canvas.create_arc(30, 200, 90, 100, start = 0, extent = 210, outline = 'red', fill = 'green', width = 2)

        canvas.create_rectangle(300, 300, 400, 400)
        canvas.create_oval(300, 300, 400, 400, outline = 'blue')

        canvas.create_rectangle(500, 200, 600, 450)
        canvas.create_oval(500, 200, 600, 450, outline = 'blue')

        canvas.create_rectangle(30, 400, 90, 300)
        canvas.create_arc(30, 400, 90, 300, start = 0, extent = 90, fill = 'red') # 0 - 90 degress

        points = [150, 100, 200, 120, 240, 180, 210, 200, 150, 150, 100, 200]
        canvas.create_polygon(points, outline = 'red', fill = 'green', width = 2)

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

