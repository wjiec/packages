package main

import "C"
import "fmt"

func main() {}

//export goPrintln
func goPrintln(s *C.char) {
	fmt.Println(C.GoString(s))
}
