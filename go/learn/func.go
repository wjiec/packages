package main

import (
	"fmt"
	"math"
)

func div(a, b int) (q, r int) {
	return a / b, a % b
}

func div2(a, b int) (q, r int) {
	q = a / b
	r = a % b
	return
}

func apply(op func(float64, float64) float64, a, b float64) float64 {
	return op(a, b)
}

func sum(numbers ...int) int {
	var sum int
	for i := range numbers {
		sum += i
	}
	return sum
}

func passByAny(a int) {
	a++
}

func main() {
	q, r := div(13, 3)
	fmt.Println(q, r)

	q1, r1 := div2(13, 3)
	fmt.Println(q1, r1)

	fmt.Println(apply(math.Pow, 3, 4))
	fmt.Println(apply(func(a float64, b float64) float64 {
		return math.Pow(a, b)
	}, 4, 5))
	fmt.Println(sum(1, 2, 3, 4, 5, 6))

	a := 0
	passByAny(a)
	fmt.Println(a)
}
