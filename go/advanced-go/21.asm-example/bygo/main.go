package main

import (
	"fmt"
	"runtime"
)

func main() {
	var buf = make([]byte, 4096)
	var stack = buf[:runtime.Stack(buf, false)]
	fmt.Printf("%s", stack)
}
