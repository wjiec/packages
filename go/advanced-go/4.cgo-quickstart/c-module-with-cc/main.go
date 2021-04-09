package main

// #include "module/hello.h"
import "C"

func main() {
	C.sayHello(C.CString("cgo"))
}
