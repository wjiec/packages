package main

import "fmt"

type Printer interface {
	Print()
}

type WordPrinter struct{}

func (*WordPrinter) Print() {}

func main() {
	var wp *WordPrinter
	fmt.Println(wp == nil)

	var p Printer = wp
	fmt.Println(p == nil)
}
