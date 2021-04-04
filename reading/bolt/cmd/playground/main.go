package main

import (
	"fmt"
	"unsafe"
)

type Node struct{
	Id uint16
	Count uint32
}

func main() {
	v := make([]byte, 6)
	n := (*Node)(unsafe.Pointer(&v[0]))

	n.Id = 1
	n.Count = 0x0304

	fmt.Println(v)
}
