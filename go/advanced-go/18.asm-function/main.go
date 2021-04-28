package main

import (
	"fmt"

	"advanced-go/18.asm-function/align"

	"advanced-go/18.asm-function/swap"
)

func main() {
	r0, r1 := swap.Swap(1, 2)
	fmt.Println(r0, r1)

	fmt.Println(align.Align(true, 0x0102))
	fmt.Println(align.Align(false, 0x0304))
}
