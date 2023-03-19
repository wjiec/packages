package main

import "fmt"

type Printer interface {
	Print()
}

type base struct{}

func (*base) Print() {}

type A struct{ base }
type B struct{ base }
type C struct{ base }

func main() {
	m := map[string]any{"a": &A{}, "b": &B{}, "c": &C{}, "d": 1}
	for _, v := range m {
		switch x := v.(type) {
		case *A:
			fmt.Printf("a => %#v\n", x)
		case *B:
			fmt.Printf("b => %#v\n", x)
		case *C:
			fmt.Printf("c => %#v\n", x)
		default:
			fmt.Printf("default => %#v\n", x)
		}
	}
}
