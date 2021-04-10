package main

// #include "hello.h"
import "C"

func main() {
	C.sayHello(C.CString("cgo"))
}
