package main

import (
	"fmt"
	"laboys.org/jayson/learn/queue_any"
)

func main() {
	q := queue_any.Queue{1, 2, 3, "abc"}
	q.Push(1).Push(123).Push(3.14)
	fmt.Printf("%T %v\n", q, q)
	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q)
}
