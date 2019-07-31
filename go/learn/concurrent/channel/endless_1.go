package main

import (
	"fmt"
	"time"
)

func work(in chan int, out chan int) {
	for {
		fmt.Println(1)
		v := <-in
		fmt.Println(2)
		fmt.Println(v)
		fmt.Println(3)
		out <- v * v
		fmt.Println(4)
	}
}

func provide(in chan int)  {
	for i := 0; i >= 0; i++ {
		in <- i
	}
}

func main() {
	in := make(chan int)
	out := make(chan int)

	go work(in, out)
	go provide(in)
	time.Sleep(time.Hour)
}
