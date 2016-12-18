#!/usr/bin/env python3
import time
import random
import tkinter as tk
import tkinter.ttk as ttk

_screen_width = 640
_screen_height = 480
_draw_delay = 100
_snake_body_size = 10
_snake_default_length = 3
_update_board_delay = 100

class Board(tk.Canvas):

    def __init__(self, parent):
        tk.Canvas.__init__(self, width = _screen_width, height = _screen_height,
            background = 'black', highlightthickness = 0)

        self.__init_attributes()
        self.__init_wall()
        self.__init_snake()
        self.__init_apple()
        self.__init_event()

        self.pack(fill = tk.BOTH, expand = True)

    def __init_attributes(self):
        self.x_max_coordinate = int(_screen_width / _snake_body_size)
        self.y_max_coordinate = int(_screen_height / _snake_body_size)
        self.action = 'RIGHT'
        self.alive = True

        self.apple_coordinate = self.__random_apple_coordinate()

    def __init_wall(self):
        for x in range(self.x_max_coordinate):
            for y in [0, self.y_max_coordinate - 1]:
                self.__draw_circle(x, y, 'wall', 'white')

        for y in range(1, self.y_max_coordinate - 1):
            for x in [0, self.x_max_coordinate - 1]:
                self.__draw_circle(x, y, 'wall', 'white')

    def __init_snake(self):
        init_snake_head_coord = (self.x_max_coordinate / 2, self.y_max_coordinate / 2)
        self.__draw_snake_head(*init_snake_head_coord)
        self.__draw_snake_body(init_snake_head_coord[0] - 1, init_snake_head_coord[1])
        self.__draw_snake_body(init_snake_head_coord[0] - 2, init_snake_head_coord[1])

    def __init_apple(self):
        while not self.__check_apple_coordinate(*self.apple_coordinate):
            self.__draw_new_apple()
        else:
            self.__draw_new_apple(*self.apple_coordinate)

    def __init_event(self):
        self.bind_all('<Key>', self.__on_key_pressed)
        self.after(_update_board_delay, self.__on_timer)

    def __on_key_pressed(self, event):
        key = event.keysym
        
        if key == 'Up':
            self.action = 'UP'
        elif key == 'Down':
            self.action = 'DOWN'
        elif key == 'Left':
            self.action = 'LEFT'
        elif key == 'Right':
            self.action = 'RIGHT'

        if self.action in [ 'UP', 'DOWN', 'LEFT', 'RIGHT' ]:
            print('[EVENT] Key-{}'.format(self.action))

    def __on_timer(self):
        if self.alive:
            self.__update_apple_coordinate()
            self.__check_collisions()
            self.__do_move_snake()
            self.after(_update_board_delay, self.__on_timer)
        else:
            self.__game_over()

    def __update_apple_coordinate(self):
        apple_coordinate = self.__get_apple_coordinate()
        snake_head_coordinate = self.__get_snake_head_coordinate()

        if apple_coordinate == snake_head_coordinate:
            apple = self.find_withtag('apple')
            self.delete(apple[0])
            self.__draw_new_apple()
            self.__add_snake_body()

    def __do_move_snake(self):
        snake_head = self.find_withtag('snake_head')
        snake_body = self.find_withtag('snake_body')

        snake_all_node = snake_body + snake_head
        for index in range(len(snake_all_node) - 1):
            last_node = self.coords(snake_all_node[index])
            prev_node = self.coords(snake_all_node[index + 1])

            self.move(snake_all_node[index], prev_node[0] - last_node[0], prev_node[1] - last_node[1])

        if self.action == 'UP':
            self.move(snake_head, 0, -_snake_body_size)
        elif self.action == 'DOWN':
            self.move(snake_head, 0, _snake_body_size)
        elif self.action == 'LEFT':
            self.move(snake_head, -_snake_body_size, 0)
        elif self.action == 'RIGHT':
            self.move(snake_head, _snake_body_size, 0)

    def __add_snake_body(self):
        snake_bodys = self.__get_snake_body_coordinate()

        self.__draw_snake_body(*snake_bodys[len(snake_bodys) - 1])

    def __random_apple_coordinate(self):
        return random.randint(1, self.x_max_coordinate - 1), random.randint(1, self.y_max_coordinate - 1)

    def __draw_snake_head(self, x, y):
        self.__draw_circle(x, y, 'snake_head', 'red')

    def __draw_snake_body(self, x, y):
        self.__draw_circle(x, y, 'snake_body', 'blue')

    def __draw_new_apple(self, x = None, y = None):
        self.apple_coordinate = self.__random_apple_coordinate()
        while not self.__check_apple_coordinate(*self.apple_coordinate):
            self.apple_coordinate = self.__random_apple_coordinate()
        self.__draw_circle(*self.apple_coordinate, 'apple', 'green')

    def __draw_circle(self, x, y, tag, fill):
        if fill is None:
            raise TypeError('fill parameter not definition')
        self.create_oval(x * _snake_body_size, y * _snake_body_size,
            (x + 1) * _snake_body_size, (y + 1) * _snake_body_size,
            outline = fill, fill = fill, tag = tag)

    def __check_apple_coordinate(self, x, y):
        snake_head_coordinate = self.__get_snake_head_coordinate()
        snake_body_coordinate = self.__get_snake_body_coordinate()

        if (x, y) in snake_head_coordinate:
            return False
        if (x, y) in snake_body_coordinate:
            return False
        if x == 0 or x == self.x_max_coordinate - 1:
            return False
        if y == 0 or y == self.y_max_coordinate - 1:
            return False
        return True

    def __check_collisions(self):
        snake_head = self.__get_snake_head_coordinate()
        snake_body = self.__get_snake_body_coordinate()

        # next coordinate
        if self.action == 'UP':
            snake_head = (snake_head[0], snake_head[1] - 1)
        elif self.action == 'DOWN':
            snake_head = (snake_head[0], snake_head[1] + 1)
        elif self.action == 'LEFT':
            snake_head = (snake_head[0] - 1, snake_head[1])
        elif self.action == 'RIGHT':
            snake_head = (snake_head[0] + 1, snake_head[1])

        if snake_head in snake_body:
            snake_body_index = self.find_withtag('snake_body')
            self.delete(snake_body_index[0])
            self.alive = False
            return False

        if snake_head[0] <= 0 or snake_head[1] <= 0:
            self.alive = False
            return False
        elif snake_head[0] >= (self.x_max_coordinate - 1) or snake_head[1] >= (self.y_max_coordinate - 1):
            self.alive = False
            return False

    def __get_apple_coordinate(self):
        apple = self.find_withtag('apple')
        
        x1, y1, x2, y2 = self.bbox(apple[0])
        return int((x1 + 1) / 10), int((y1 + 1) / 10)

    def __get_snake_head_coordinate(self):
        snake_head = self.find_withtag('snake_head')

        x1, y1, x2, y2 = self.bbox(snake_head)
        return int((x1 + 1) / 10), int((y1 + 1) / 10)

    def __get_snake_body_coordinate(self):
        snake_body = self.find_withtag('snake_body')

        snake_body_coordinate = []
        for body in snake_body:
            x1, y1, x2, y2 = self.bbox(body)
            snake_body_coordinate.append((int((x1 + 1) / 10), int((y1 + 1) / 10)))
        return snake_body_coordinate

    def __game_over(self):
        self.delete(tk.ALL)
        self.create_text(self.winfo_width() / 2, self.winfo_height() / 2, text='Game Over', fill = 'white', font = 'Segoe\ Print')

class Nibbles(tk.Frame):

    def __init__(self, parent):
        tk.Frame.__init__(self, parent)

        self.master.title('Nibbles')
        self.board = Board(self.master)

        self.pack(fill = tk.BOTH, expand = True)

if __name__ == '__main__':
    root = tk.Tk()
    nibbles = Nibbles(root)

    root.mainloop()
