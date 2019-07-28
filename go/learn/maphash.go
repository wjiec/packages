package main

import "fmt"

type Coordinate struct {
	x int
	y int
}

func main()  {
	m := map[Coordinate]int {
		{1, 1}: 2,
		{1, 2}: 3,
		{2, 1}: 3,
		{2, 2}: 4,
	}

	v, ok := m[Coordinate{2, 2}]
	fmt.Println(v, ok)
}
