package main

/*
#include <stdio.h>

static void dumpGoString(_GoString_ s) {
	printf("C: %d %#x\n", _GoStringLen(s), _GoStringPtr(s));
	printf("C: %s\n", _GoStringPtr(s)); // invalid access
}
*/
import "C"
import (
	"fmt"
	"unsafe"
)

func main() {
	s := "hello from go"
	fmt.Println(unsafe.Pointer(&s))
	C.dumpGoString(s)
}
