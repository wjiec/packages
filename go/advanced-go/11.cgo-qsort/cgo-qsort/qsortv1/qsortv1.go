package qsortv1

/*
#include <stdlib.h>

typedef int (*qsort_compar_func)(const void*, const void*);

// void qsort(void *base, size_t nitems, size_t size, int (*compar)(const void *, const void*)
*/
import "C"
import "unsafe"

type Comparator C.qsort_compar_func

func Sort(base unsafe.Pointer, count, size int, comparator Comparator) {
	C.qsort(base, C.size_t(count), C.size_t(size), C.qsort_compar_func(comparator))
}
