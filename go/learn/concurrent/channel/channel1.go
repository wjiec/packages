package main

import (
	"fmt"
	"time"
)

func main() {
	c := make(chan int)
	go func() {
		for  {
			fmt.Println(<-c)
		}
	}()

	go func() {
		for i := 0; i >= 0; i++ {
			c <- i
		}
	}()

	time.Sleep(time.Second)
}
