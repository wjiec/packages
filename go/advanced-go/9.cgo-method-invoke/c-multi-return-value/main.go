package main

/*
#include <errno.h>

static int sum(int a, int b) {
	if (a <= 0 || b <= 0) {
		errno = EINVAL;
		return 0;
	}
	return a + b;
}
*/
import "C"
import "fmt"

func main() {
	v0, err0 := C.sum(1, 2)
	fmt.Printf("%T %v, %T, %v\n", v0, v0, err0, err0)

	v1, err1 := C.sum(0, 1)
	fmt.Printf("%T %v, %T, %v\n", v1, v1, err1, err1)
}
