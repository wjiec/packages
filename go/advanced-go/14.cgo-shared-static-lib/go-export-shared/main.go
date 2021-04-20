package main

import "C"
import (
	"fmt"

	_ "advanced-go/14.cgo-shared-static-lib/go-export-shared/number"
)

func main() {}

//export goPrintln
func goPrintln(s *C.char) {
	fmt.Println(C.GoString(s))
}
