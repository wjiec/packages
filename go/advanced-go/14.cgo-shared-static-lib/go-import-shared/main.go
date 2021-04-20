package main

/*
#cgo CFLAGS: -I${SRCDIR}/number
#cgo LDFLAGS: -L${SRCDIR}/number -lnumber

#include "number.h"
*/
import "C"
import "fmt"

func main() {
	fmt.Printf("%#v", C.add_number(C.int(1), C.int(2)))
}
