package main

import (
	"context"
	"fmt"
	"math/rand"
	"time"
)

func Println(ctx context.Context, a, b int) {
	for {
		fmt.Println(a, b, a+b)
		a, b = a+1, b+1
		time.Sleep(time.Duration(rand.Intn(200)) * time.Millisecond)
		select {
		case <-ctx.Done():
			fmt.Println("程序结束")
			return
		default:
		}
	}
}

func main() {
	{
		a := 1
		b := 2
		timeout := 2 * time.Second
		ctx, _ := context.WithTimeout(context.Background(), timeout)
		Println(ctx, a, b)

		time.Sleep(2 * time.Second)
	}

	{
		a := 1
		b := 2
		ctx, cancelCtx := context.WithCancel(context.Background())
		go func() {
			time.Sleep(2 * time.Second)
			cancelCtx() // manual cancel
		}()
		Println(ctx, a, b)

		time.Sleep(2 * time.Second)
	}
}
