#ifndef __QSORT_INCLUDED__
#define __QSORT_INCLUDED__

#include <stdint.h>

void doSort(void *base, size_t count, size_t size, void *fn);
extern int cgoCall(void*, void*, void*);

#endif // __QSORT_INCLUDED__
