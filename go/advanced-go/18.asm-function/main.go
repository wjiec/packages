package main

import (
	"fmt"

	"advanced-go/18.asm-function/caller"

	"advanced-go/18.asm-function/addr"

	"advanced-go/18.asm-function/align"

	"advanced-go/18.asm-function/swap"
)

func main() {
	r0, r1 := swap.Swap(1, 2)
	fmt.Println(r0, r1)

	fmt.Println(align.Align(true, 0x0102))
	fmt.Println(align.Align(false, 0x0304))
	addr.Check()
	fmt.Println()

	caller.PrintSub(1, 2)
	caller.PrintSub(3, 2)
	caller.PrintSub(-99, -1)
	caller.PrintSub(101, 201)
}
