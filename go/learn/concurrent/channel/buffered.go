package main

import (
	"fmt"
	"time"
)

func main() {
	c := make(chan int, 3)

	c <- -1
	c <- -2
	c <- -3
	//c <- -4

	go func() {
		for {
			if n, ok := <- c; ok {
				fmt.Println(n, ok)
			}
		}
	}()

	time.Sleep(3 * time.Second)
}
