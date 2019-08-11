#!/usr/bin/env python3
#
# Copyright (C) 2019
#
from collections import namedtuple


Coordinate = namedtuple('Coordinate', 'x y')


class Maze(object):

    def __init__(self):
        self._maze = [
            [0, 1, 0, 0, 0, 1, 1, 1, 0],
            [0, 0, 0, 1, 0, 0, 0, 0, 0],
            [0, 1, 1, 1, 0, 1, 1, 0, 1],
            [0, 1, 1, 0, 0, 1, 1, 0, 0],
            [1, 0, 0, 0, 1, 0, 0, 0, 1],
            [1, 1, 1, 1, 1, 0, 1, 0, 0],
            [0, 1, 0, 0, 0, 0, 0, 1, 0],
            [0, 1, 0, 1, 1, 1, 1, 1, 1],
            [0, 0, 0, 0, 0, 0, 0, 0, 0],
        ]

    def is_road(self, coordinate: Coordinate) -> bool:
        if coordinate.x < 0 or coordinate.x >= len(self._maze):
            return False
        if coordinate.y < 0 or coordinate.y >= len(self._maze[coordinate.x]):
            return False
        return self._maze[coordinate.x][coordinate.y] == 0

    def is_wall(self, coordinate: Coordinate) -> bool:
        return not self.is_road(coordinate)

    @staticmethod
    def clockwise(coordinate: Coordinate) -> list:
        return [
            Coordinate(coordinate.x - 1, coordinate.y),  # top
            Coordinate(coordinate.x, coordinate.y + 1),  # right
            Coordinate(coordinate.x + 1, coordinate.y),  # bottom,
            Coordinate(coordinate.x, coordinate.y - 1),  # left
        ]

    @property
    def raw_data(self):
        return self._maze


class Walker(object):

    def __init__(self, maze: Maze):
        self._maze = maze
        self._record = self._clone_recorder(self._maze.raw_data)

    def walk(self, start: Coordinate, end: Coordinate) -> list:
        if self._maze.is_wall(start) or self._maze.is_wall(end):
            raise Exception('maze start or end unreachable')
        return self._traverse(start)

    def _traverse(self, start: Coordinate) -> list:
        queue = [start]
        while len(queue):
            current, queue = queue[0], queue[1:]
            for coordinate in Maze.clockwise(current):
                if coordinate == start:
                    continue
                if self._maze.is_wall(coordinate):
                    continue
                if self._record[coordinate.x][coordinate.y] != 0:
                    continue
                queue.append(coordinate)
                self._record[coordinate.x][coordinate.y] = self._record[current.x][current.y] + 1
        return self._record

    @staticmethod
    def _clone_recorder(maze: list):
        return [[0 for _ in r] for r in maze]


if __name__ == '__main__':
    paths = Walker(Maze()).walk(Coordinate(0, 0), Coordinate(8, 8))
    for row in paths:
        for col in row:
            print('{0:4}'.format(col), end='', sep=' ')
        print()
