package main

import (
	"fmt"
	"unsafe"

	"advanced-go/11.cgo-qsort/cgo-qsort/qsortv1"
	"advanced-go/11.cgo-qsort/cgo-qsort/qsortv2"
)

/*
extern int v1Cmp(void*, void*);
*/
import "C"

//export v1Cmp
func v1Cmp(l, r unsafe.Pointer) C.int {
	return C.int(*(*int)(l) - *(*int)(r))
}

func main() {
	numbers := [...]int{2, 1, 3, 5, 4, 6, 9, 8, 0}
	fmt.Printf("Numbers: %#v\n", numbers)

	v1(numbers)
	v2(numbers)
}

func v1(numbers [9]int) {
	qsortv1.Sort(unsafe.Pointer(&numbers[0]), len(numbers), int(unsafe.Sizeof(numbers[0])), qsortv1.Comparator(C.v1Cmp))
	fmt.Printf("qsortV1: %#v\n", numbers)
}

func v2(numbers [9]int) {
	qsortv2.Sort(unsafe.Pointer(&numbers[0]), len(numbers), int(unsafe.Sizeof(numbers[0])), func(l, r unsafe.Pointer) int {
		return *(*int)(l) - *(*int)(r)
	})
	fmt.Printf("qsortV2: %#v\n", numbers)
}
