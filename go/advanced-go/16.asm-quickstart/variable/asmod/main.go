package main

import (
	"fmt"
	"unsafe"

	"advanced-go/16.asm-quickstart/variable/asmod/num"
	"advanced-go/16.asm-quickstart/variable/asmod/str"
)

func main() {
	var v int
	fmt.Println(unsafe.Sizeof(v))

	fmt.Printf("%#x\n", num.Id)
	fmt.Println(str.Name)
	fmt.Println(str.Truncation)
}
