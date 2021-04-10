package main

// void sayHello1(char *s);
// void sayHello2(_GoString_ s);
import "C"
import "fmt"

//export sayHello1
func sayHello1(s *C.char) {
	fmt.Println(C.GoString(s))
}

//export sayHello2
func sayHello2(s string) {
	fmt.Println(s)
}

func main() {
	// go -> cgo -> go
	C.sayHello1(C.CString("hello cgo by *C.char"))
	C.sayHello2("hello cgo by _GoString_")
}
