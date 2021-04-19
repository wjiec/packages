package qsortv2

/*
#include "qsort.h"
*/
import "C"
import (
	"unsafe"
)

//export cgoCall
func cgoCall(fn unsafe.Pointer, a, b unsafe.Pointer) C.int {
	c := *(*Comparator)(fn)
	return C.int(c(a, b))
}

type Comparator func(a, b unsafe.Pointer) int

func Sort(base unsafe.Pointer, count, size int, c Comparator) {
	C.doSort(base, C.size_t(count), C.size_t(size), unsafe.Pointer(&c))
}
