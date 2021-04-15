package main

/*
extern int v1CmpFunc(void*, void*);
*/
import "C"
import (
	"fmt"
	"unsafe"

	"advanced-go/11.cgo-qsort/cgo-qsort/qsortv1"
)

func main() {
	values := []int{1, 3, 2, 6, 4, 5, 9, 8, 7}
	fmt.Println(values)

	v1(nCopy(values))
}

//export v1CmpFunc
func v1CmpFunc(a, b unsafe.Pointer) C.int {
	l, r := (*C.int)(a), (*C.int)(b)

	return *l - *r
}

func v1(values []int) {
	base := unsafe.Pointer(&values[0])
	size := int(unsafe.Sizeof(values[0]))

	qsortv1.Sort(base, len(values), size, qsortv1.CompareFunc(v1CmpFunc))
	fmt.Printf("V1: %#v\n", values)
}

func nCopy(values []int) []int {
	c := make([]int, len(values))
	copy(c[0:], values)

	return c
}
