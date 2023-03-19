package main

import "fmt"

func main() {
	a := make([]int, 3, 4)
	b := append(a, 4)

	fmt.Println(a)
	fmt.Println(b)

	a[0] = 1
	a[1] = 2
	b[2] = 3

	fmt.Println(a)
	fmt.Println(b)

	c := append(a, 5)
	fmt.Println(c)

	d := append(a, 6, 7)
	fmt.Println(d)
}
