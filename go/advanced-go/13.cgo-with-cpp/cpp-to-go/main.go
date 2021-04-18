package main

/*
#include <stdio.h>
*/
import "C"
import (
	"fmt"

	"advanced-go/13.cgo-with-cpp/cpp-to-go/buffer"
)

func main() {
	buf := buffer.New(1024)
	defer buf.Free()

	copy(buf.Data(), []byte("Hello world from go"))
	fmt.Printf("Go: %d -> %#v\n", buf.Size(), buf.Data())
	buf.Print()
}
