#ifndef __BUFFER_C_API_INCLUDED__
#define __BUFFER_C_API_INCLUDED__

#include <string.h>

typedef struct buffer_t *CBuffer;

CBuffer newBuffer(size_t sz);
void deleteBuffer(CBuffer buf);
size_t bufferSize(CBuffer buf);
const char *bufferData(CBuffer buf);
void printBuffer(CBuffer buf);

#endif // __BUFFER_C_API_INCLUDED__
