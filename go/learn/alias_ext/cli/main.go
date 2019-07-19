package main

import (
	"fmt"

	"laboys.org/jayson/learn/alias_ext"
)

func main() {
	q := alias_ext.Queue{}
	fmt.Println(q.Empty())

	q.Push(1)
	fmt.Println(q)

	q.Push(2)
	fmt.Println(q)

	fmt.Println(q.Pop())
	fmt.Println(q.Empty())

	fmt.Println(q.Pop())
	fmt.Println(q.Empty())

	fmt.Println(q.Pop())
	fmt.Println(q.Empty())
}
