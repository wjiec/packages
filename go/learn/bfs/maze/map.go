package maze

type Map [][]int

const (
	RoadBlock = 0
	WallBlock = 1
	RB        = RoadBlock
	WB        = WallBlock
)

func New(m [][]int) Map {
	maze := make([][]int, len(m))
	copy(maze, m)
	return Map(maze)
}

func (m *Map) IsWall(c Coordinate) bool {
	return !m.IsRoad(c)
}

func (m *Map) IsRoad(c Coordinate) bool {
	return !m.IsEmpty(c) && (*m)[c.X][c.Y] == RB
}

func (m *Map) IsEmpty(c Coordinate) bool {
	if c.X < 0 || c.X >= len(*m) {
		return true
	}

	if c.Y < 0 || c.Y >= len((*m)[c.X]) {
		return true
	}

	return false
}

func (m *Map) ClockWise(c Coordinate) []Coordinate {
	return []Coordinate{c.Top(), c.Right(), c.Bottom(), c.Left()}
}
