package main

import "fmt"

func Add(a int, more ...int) (v int) {
	defer func() { v = -v }()

	v = a
	for _, i := range more {
		v += i
	}
	return
}

func Print(v ...int)  {
	for _, i := range v {
		//
		//i := i
		//
		//noinspection GoDeferInLoop
		defer func() { fmt.Println(i) }()
	}
}

type Value struct {
	v *int
}

func (v *Value) Get() int {
	return *v.v
}

var GetValue = (*Value).Get

func main() {
	fmt.Println(Add(1, 2, 3, 4, 5, 6, 7))
	Print(1, 2, 3, 4, 5, 6, 7)

	n := 8
	v := Value{v: &n}
	fmt.Println(v.Get(), GetValue(&v))
	fmt.Println()
}
