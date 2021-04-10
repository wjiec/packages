package main

// static const char *s = "hello from cgo";
import "C"
import (
	"fmt"

	"advanced-go/5.cgo-basic/go-isolate-c-package/helper"
)

func main() {
	helper.PrintCString(C.s)

	s := helper.CChar(*C.s)
	fmt.Println(s.GoString())
}
