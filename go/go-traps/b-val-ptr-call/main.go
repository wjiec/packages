package main

import "fmt"

type People struct{}

func (p People) Foo() {
	fmt.Println("People.Foo")
}

func (p *People) Bar() {
	fmt.Println("People.Bar")
}

func main() {
	var p People
	p.Bar() // value

	var pp *People = &p
	pp.Foo() // ptr
}
