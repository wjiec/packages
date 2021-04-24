package main

import (
	"fmt"
	"unsafe"

	"advanced-go/16.asm-quickstart/number-variable/asmod/str"

	"advanced-go/16.asm-quickstart/number-variable/asmod/num"
)

func main() {
	var v int
	fmt.Println(unsafe.Sizeof(v))

	fmt.Printf("%#x\n", num.Id)
	fmt.Println(str.Name)
	fmt.Println(str.Truncation)
}
