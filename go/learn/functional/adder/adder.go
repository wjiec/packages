package main

import "fmt"

type Adder func(v int) int

func adder(initial int) Adder {
	return func(v int) int {
		initial += v
		return initial
	}
}

func main() {
	a := adder(0)
	for i := 0; i < 10; i++ {
		fmt.Println(a(i))
	}
}
