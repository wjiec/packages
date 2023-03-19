package main

import "fmt"

type Greeting struct{}

func (*Greeting) Say() {
	fmt.Println("hello")
}

func main() {
	var g *Greeting
	fmt.Println(g == nil)
	g.Say()
}
