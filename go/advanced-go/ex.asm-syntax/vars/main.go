package main

import (
	"fmt"

	"advanced-go/ex.asm-syntax/vars/exports"
)

func main() {
	fmt.Println(exports.ArrayValue)
	fmt.Println(exports.BoolValue)
	fmt.Println(exports.TrueValue)
	fmt.Println(exports.FalseValue)
	fmt.Println(exports.IntValue)
	fmt.Println(exports.Float32Value)
	fmt.Println(exports.Float64Value)
	fmt.Println(exports.StringValue)
	fmt.Println(exports.SliceValue)
}
