package main

import (
	"fmt"
	"math"
)

func main() {
	const filename = "basic.go"
	const a, b = /* int */ 3, 4
	//            ^^^
	c := math.Sqrt(a*a + b*b)
	fmt.Println(filename, c)
}
