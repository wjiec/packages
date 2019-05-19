package main

import (
	"fmt"
	"math"
	"math/cmplx"
)

func euler() {
	cc := 3 + 4i
	fmt.Println(cmplx.Abs(cc))

	zero := cmplx.Pow(math.E, 1i * math.Pi) + 1
	fmt.Println(zero)

	zero1 := cmplx.Exp(1i * math.Pi) + 1
	fmt.Println(zero1)
	fmt.Printf("%2.3f\n", zero1)

	// Python
	// >>> import cmath
	// >>> cmath.exp(1j + cmath.pi) + 1
	// (13.502969588876512+19.472221418841606j)
}

func explicit() {
	a, b := 3, 4
	var c float64 // int
	c = math.Sqrt(float64(a * a + b * b))
	//            ^^^^^^
	fmt.Println(c)
}

func main() {
	var i int = 0xffffffffff
	fmt.Printf("%#x\n", i)

	var ui uint = 0xffffffffff
	fmt.Printf("%#x\n", ui)

	//var i8 int8 = 0xffff
	//fmt.Printf("%#x", i8)

	euler()

	explicit()
}
