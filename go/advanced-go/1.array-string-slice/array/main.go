package main

import (
	"fmt"
	"unsafe"
)

func main() {
	var a [3]int
	var b = [...]int{0, 1, 2, 3}
	var c = [...]int{1: 5, 0: 4, 3: 6}
	var d = [...]int{7, 8, 5: 9, 10}
	fmt.Println(a, b, c, d)

	// copy
	var e = d
	d[0] = -1
	fmt.Println(d, e)

	// empty array
	var f [0]int
	fmt.Printf("size of f(%T %#v): %d\n", f, f, unsafe.Sizeof(f))
}
