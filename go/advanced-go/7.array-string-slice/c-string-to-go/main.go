package main

/*
#include <stdlib.h>
#include <string.h>

static char *create_string() {
	char *s = (char*)malloc(sizeof(char) * 32);
	strcpy(s, "hello");

	return s;
}

static void free_string(char *s) {
	free(s);
}
*/
import "C"
import (
	"fmt"
	"reflect"
	"unsafe"
)

func main() {
	var s string

	data := C.create_string()
	header := (*reflect.StringHeader)(unsafe.Pointer(&s))
	header.Data = uintptr(unsafe.Pointer(data))
	header.Len = int(C.strlen(data))
	fmt.Println(s)

	C.free_string(data)
	fmt.Println(s) // invalid memory access
}
