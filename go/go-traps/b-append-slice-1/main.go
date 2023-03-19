package main

import "fmt"

func main() {
	a := make([]int, 3, 4)
	b := append(a, 4)

	fmt.Println(a)
	fmt.Println(b)
}
