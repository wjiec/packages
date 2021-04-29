package addr

import (
	"fmt"
	"reflect"
	"unsafe"
)

func Check() {
	a := []byte{0}
	var b int16
	var c bool
	var d int16
	var e int16
	var f int16

	fmt.Printf("a = %x\n", &a) // &00
	fmt.Printf("b = %x\n", &b) // c0000100dc
	fmt.Printf("c = %x\n", &c) // c0000100de
	fmt.Printf("d = %x\n", &d) // c0000100f0
	fmt.Printf("f = %x\n", &e) // c0000100f2
	fmt.Printf("g = %x\n", &f) // c0000100f4

	h := *(*reflect.SliceHeader)(unsafe.Pointer(&a))
	fmt.Printf("a.data = %x(val = %x)\n", &h.Data, h.Data) // c0000044e0
	fmt.Printf("a.len = %x\n", &h.Len)                     // c0000044f0
	fmt.Printf("a.cap = %x\n", &h.Cap)                     // c0000044e8
}
