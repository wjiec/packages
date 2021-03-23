package main

import (
	"fmt"
	"reflect"
	"unsafe"
)

func main() {
	s := "hello world"
	fmt.Printf("len(s) = %d\n", (*reflect.StringHeader)(unsafe.Pointer(&s)).Len)

	var data = [...]byte{'H', 'E', 'L', 'L', 'O', 'W', 'O', 'R', 'L', 'D',}
	ss := (*string)(unsafe.Pointer(&reflect.StringHeader{Data:uintptr(unsafe.Pointer(&data)), Len:len(data)}))
	fmt.Printf("%s\n", *ss)

	// rune = int32
	fmt.Printf("[]rune{} => %T\n", [0]rune{})
}
