#ifndef __MEM_C_API_INCLUDED__
#define __MEM_C_API_INCLUDED__

#include <stdint.h>

typedef uintptr_t RefAddr;

extern void unref(RefAddr);


#endif // __MEM_C_API_INCLUDED__
