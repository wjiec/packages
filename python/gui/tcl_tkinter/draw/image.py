#!/usr/bin/env python3
import tkinter as tk
import tkinter.ttk as ttk
from PIL import Image, ImageTk

class CanvasDraw(tk.Frame):

    def __init__(self, parent):
        super(CanvasDraw, self).__init__(parent)

        self.__init_window_ui()

    def __init_window_ui(self):
        self.master.title('Canvas Draw Image')
        self.pack(fill = tk.BOTH, expand = True)
        self.style = ttk.Style()

        self.style.configure("TFrame", background = '#333')

        # self.__init_window_size(640, 480)
        self.__init_canvas()

    def __init_canvas(self):
        self.image = Image.open('1.png')
        self.image_tk = ImageTk.PhotoImage(self.image)

        canvas = tk.Canvas(self, width = self.image.size[0] + 20, height = self.image.size[1] + 20)

        canvas.create_rectangle(2, 2, self.image.size[0] + 20, self.image.size[1] + 20)
        canvas.create_image(10, 10, anchor = tk.NW, image = self.image_tk)

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

