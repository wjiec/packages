package main

import (
	"context"
	"fmt"
	"math/rand"
	"time"
)

func worker(ctx context.Context) <-chan int {
	c := make(chan int)

	go func() {
		for {
			select {
			case <-ctx.Done():
				fmt.Println("receive cancel")
				return
			case c <- rand.Intn(10):
			}
			time.Sleep(time.Duration(rand.Intn(1000)) * time.Millisecond)
		}
	}()

	return c
}

func main() {
	ctx, cancel := context.WithCancel(context.Background())

	for v := range worker(ctx) {
		fmt.Println("work result:", v)
		if v == 9 {
			cancel()
			break
		}
	}

	time.Sleep(time.Second)
	fmt.Println("main exited")
}
