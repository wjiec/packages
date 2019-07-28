package walker

import (
	"errors"

	"laboys.org/jayson/learn/bfs/maze"
)

type Walker struct{}

func (w *Walker) Walk(m *maze.Map, s maze.Coordinate, e maze.Coordinate) (paths []maze.Coordinate, err error) {
	if !m.IsRoad(s) || !m.IsRoad(e) {
		return nil, errors.New("invalid start or end coordinate")
	}

	checked := map[maze.Coordinate]int{s: 0}
	return doWalk(m, s, e, &checked, 1)
}

func doWalk(m *maze.Map, s maze.Coordinate, e maze.Coordinate, cc *map[maze.Coordinate]int, indent int) ([]maze.Coordinate, error) {
	q := []maze.Coordinate{s}
	for len(q) != 0 {
		var tq []maze.Coordinate
		for _, s := range q {
			for _, c := range m.ClockWise(s) {
				if _, ok := (*cc)[c]; !ok && m.IsRoad(c) {
					(*cc)[c] = indent
					tq = append(tq, c)

					if c.Equal(e) {
						return buildPaths(c, cc), nil
					}
				}
			}
		}
		q = tq
		indent++
	}
	return q, errors.New("step not found")
}

func buildPaths(c maze.Coordinate, cc *map[maze.Coordinate]int) []maze.Coordinate {
	paths := []maze.Coordinate{c}
	for s := (*cc)[c]; s != 0; {
		for _, p := range []maze.Coordinate{c.Top(), c.Right(), c.Bottom(), c.Left()} {
			if v, ok := (*cc)[p]; ok && v == s-1 {
				c, s = p, v
				paths = append([]maze.Coordinate{c}, paths...)
			}
		}
	}
	return paths
}
