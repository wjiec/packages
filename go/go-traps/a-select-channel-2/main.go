package main

import "fmt"

func main() {
	ch := make(chan int)
	close(ch)

	for i := 0; i < 10; i++ {
		select {
		case v := <-ch:
			fmt.Println(v)
		default:
			fmt.Println("default")
		}
	}
}
