package main

//void sayHello(const char *str);
import "C"

func main() {
	C.sayHello(C.CString("hello cgo"))
}
