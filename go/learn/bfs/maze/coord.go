package maze

import "fmt"

type Coordinate struct {
	X int
	Y int
}

func (c *Coordinate) String() string {
	return fmt.Sprintf("<%d:%d>", c.X, c.Y)
}

func (c *Coordinate) Equal(o Coordinate) bool {
	return c.X == o.X && c.Y == o.Y
}

func (c *Coordinate) Top() Coordinate {
	return Coordinate{X: c.X - 1, Y: c.Y}
}

func (c *Coordinate) Right() Coordinate  {
	return Coordinate{X: c.X, Y: c.Y + 1}
}

func (c *Coordinate) Bottom() Coordinate {
	return Coordinate{X: c.X + 1, Y: c.Y}
}

func (c *Coordinate) Left() Coordinate  {
	return Coordinate{X: c.X, Y: c.Y - 1}
}
