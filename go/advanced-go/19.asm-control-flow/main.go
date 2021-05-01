package main

import (
	"fmt"

	"advanced-go/19.asm-control-flow/forstat"

	"advanced-go/19.asm-control-flow/ifstat"
)

func main() {
	fmt.Println(ifstat.If(true, 1, 2))
	fmt.Println(ifstat.If(false, 1, 2))
	fmt.Println(forstat.Add(100, 0, 1))
	fmt.Println(forstat.Add(4, 0, 0xff))
}
