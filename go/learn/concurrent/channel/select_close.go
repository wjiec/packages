package main

import (
	"fmt"
	"time"
)

func main() {
	defer func() {
		fmt.Println("main exited")
	}()

	c := make(chan struct{})

	go func() {
		for {
			select {
			case <-c:
				fmt.Println("receive from channel c")
			default:
				fmt.Println("default case")
			}

			time.Sleep(time.Second)
		}
	}()

	go func() {
		time.Sleep(5 * time.Second)
		close(c)
		fmt.Println("channel c closed")
	}()

	time.Sleep(10 * time.Second)
}
