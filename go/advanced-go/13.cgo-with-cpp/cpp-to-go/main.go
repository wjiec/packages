package main

/*
#include <stdio.h>
*/
import "C"
import (
	"fmt"
	"unsafe"

	"advanced-go/13.cgo-with-cpp/cpp-to-go/buffer"
)

func main() {
	buf := buffer.New(128)
	defer buf.Free()

	copy(buf.Data(), []byte("Hello world from go"))
	fmt.Printf("Go: %d -> %#v\n\n", buf.Size(), buf.Data())
	buf.Print()

	C.puts((*C.char)(unsafe.Pointer(&buf.Data()[0])))
}
