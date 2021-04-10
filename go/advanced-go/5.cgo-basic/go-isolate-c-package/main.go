package main

// const char *s = "hello from cgo";
import "C"
import (
	"fmt"

	"advanced-go/5.cgo-basic/go-isolate-c-package/helper"
)

func main() {
	fmt.Println(C.GoString(C.s))

	// it's ok
	fmt.Println((*helper.CChar)(C.s).GoString())

	// not compatible
	// cannot use *_Cvar_s (type *_Ctype_char) as type *helper._Ctype_char in argument to helper.PrintCString
	//                           ^^^^^^^^^^^^          ^^^^^^^^^^^^^^^^^^^
	helper.PrintCString(C.s)                  // comment me to compile
	helper.PrintCString((*helper.CChar)(C.s)) // comment me to compile
}
