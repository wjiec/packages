package main

import "fmt"

func main() {
	a := 1
	// only :=
	a, b := 10, 20
	fmt.Println(a, b)

	c := 1
	d := 2
	// only =
	c, d = 100, 200
	fmt.Println(c, d)
}
