package main

import "fmt"

func main() {
	var ch chan int
	fmt.Println(ch == nil)
	ch <- 1
}
