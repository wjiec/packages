package main

import (
	"fmt"
	"laboys.org/jayson/learn/bfs/maze"
	"laboys.org/jayson/learn/bfs/walker"
)

type BFS interface {
	Walk(m *maze.Map, s maze.Coordinate, e maze.Coordinate) (paths []maze.Coordinate, err error)
}

func main() {
	m := maze.New([][]int{
		{0, 1, 0, 0, 0, 0},
		{0, 0, 0, 1, 0, 0},
		{0, 1, 0, 1, 0, 0},
		{1, 1, 1, 0, 0, 0},
		{0, 1, 0, 0, 1, 1},
		{0, 1, 0, 0, 0, 0},
	})

	var w BFS
	w = &walker.Walker{}

	paths, err := w.Walk(&m, maze.Coordinate{X: 0, Y: 0}, maze.Coordinate{X: 5, Y: 5})
	if err != nil {
		panic(err)
	}
	fmt.Println(paths)
}
