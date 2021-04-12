package main

import (
	"fmt"
	"unsafe"
)

type Number struct {
	b0 uint8
	b1 uint8
}

func main() {
	addr := uintptr(0x45e1c2)
	ptr := unsafe.Pointer(addr)
	num := (*Number)(ptr)
	fmt.Printf("%T %v", num, num)
}
