package main

import "fmt"

func main() {
	ch := make(chan int, 3)

	ch <- 1
	fmt.Println(len(ch))
	fmt.Println(cap(ch))
}
