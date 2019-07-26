package main

import (
	"fmt"
	"time"
)

func main() {
	c := make(chan int)
	go func() {
		for n := range c {
			fmt.Println(n)
		}
		fmt.Println("exit endless loop")
	}()

	go func() {
		for i := 0; i >= 0; i++ {
			c <- i
			// how to detecting?
			// panic: send on closed channel
		}
	}()

	time.Sleep(time.Second)
	close(c)
	fmt.Println("closed")
	time.Sleep(time.Second)
}
