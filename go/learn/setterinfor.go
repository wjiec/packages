package main

import "fmt"

func main()  {
	m := [][]int {
		{0, 1, 2, 3},
		{2, 3, 4},
		{3},
	}

	for _, row := range m {
		row = append(row, 0)
	}

	fmt.Println(m)
}
