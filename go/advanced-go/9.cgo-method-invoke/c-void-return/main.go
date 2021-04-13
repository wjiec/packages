package main

/*
#include <errno.h>

static void check() {
	errno = EINVAL;
}
*/
import "C"
import "fmt"

func main() {
	C.check()

	v0 := C.check()
	fmt.Printf("%T %v\n", v0, v0)

	v1, err1 := C.check()
	fmt.Printf("%T %v, %T %v\n", v1, v1, err1, err1)
}
