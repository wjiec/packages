package main

import "fmt"

var Name = "hello world"

/**
main.main: relocation target runtime.printstring not defined for ABI0 (but is defined for ABIInternal)
main.main: relocation target runtime.printnl not defined for ABI0 (but is defined for ABIInternal)

https://github.com/chai2010/advanced-go-programming-book/issues/427
https://github.com/golang/proposal/blob/master/design/27539-internal-abi.md
*/
func main()

func println(s string) {
	fmt.Println(s)
}
