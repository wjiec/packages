package main

import (
	"fmt"

	"advanced-go/17.asm-basic/vars/exports"
	"advanced-go/17.asm-basic/vars/textflag"
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
	fmt.Println()

	fmt.Println(textflag.PointValue)
	fmt.Println(textflag.ArrayValue)
	fmt.Println()

	textflag.ArrayValue[0] = 666
	textflag.ArrayValue[1] = 888
	fmt.Println(textflag.PointValue)
	fmt.Println(textflag.ArrayValue)
	fmt.Println()

	defer func() {
		fmt.Printf("Error: %s\n", recover())
	}()

	fmt.Println(textflag.IntValue)
	textflag.IntValue = 99999
	fmt.Println(textflag.IntValue)
}
