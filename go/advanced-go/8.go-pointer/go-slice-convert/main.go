package main

import (
	"fmt"
	"reflect"
	"runtime"
	"unsafe"
)

func main() {
	ns := []int{0x12345678, 0x87654321, 0xabcdef01, 0x10fedcba}
	var bs []uint16

	nsh := (*reflect.SliceHeader)(unsafe.Pointer(&ns))
	bsh := (*reflect.SliceHeader)(unsafe.Pointer(&bs))

	bsh.Data = nsh.Data // replace
	bsh.Len = nsh.Len * int(unsafe.Sizeof(ns[0])) / int(unsafe.Sizeof(bs[0]))
	bsh.Cap = nsh.Cap * int(unsafe.Sizeof(ns[0])) / int(unsafe.Sizeof(bs[0]))

	fmt.Printf("%T %v\n", ns, ns)
	fmt.Printf("%T %v\n", bs, bs)

	ns = nil
	runtime.GC()

	fmt.Printf("%T %v\n", ns, ns)
	fmt.Printf("%T %v\n", bs, bs)

	ns = []int{0x01020304, 0x04030201}
	runtime.GC()

	fmt.Printf("%T %v\n", ns, ns)
	fmt.Printf("%T %v\n", bs, bs)
}
