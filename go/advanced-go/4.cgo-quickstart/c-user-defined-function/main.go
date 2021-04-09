package main

/*
#include <stdio.h>

static void sayHello(const char *str) {
	puts(str);
}
*/
import "C"

func main() {
	C.sayHello(C.CString("hello cgo"))
}
