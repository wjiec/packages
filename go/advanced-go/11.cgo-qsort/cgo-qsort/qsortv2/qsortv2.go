package qsortv2

/*
#include <stdlib.h>

typedef int (*qsort_compar_func)(const void*, const void*);

static void _sort(void *fn, void *base, size_t count, size_t size) {
	qsort(base, count, size, fn()));
}

// void qsort(void *base, size_t nitems, size_t size, int (*compar)(const void *, const void*)
*/
import "C"
import (
	"unsafe"
)

type Comparator func(a, b unsafe.Pointer) int

func Sort(base unsafe.Pointer, count, size int, c Comparator) {
	C._sort(unsafe.Pointer(&c), base, C.size_t(count), C.size_t(size))
}
