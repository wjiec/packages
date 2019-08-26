package main

import (
	"context"
	"fmt"
	"log"
	"time"
)

func doTimeOutStuff(ctx context.Context) {
	for {
		time.Sleep(1 * time.Second)
		if deadline, ok := ctx.Deadline(); ok {
			fmt.Println("deadline set : ", deadline.Unix())
			if time.Now().After(deadline) {
				log.Fatal("错误：", ctx.Err().Error())
			}
		}

		select {
		case <-ctx.Done():
			fmt.Println("Done")
			return
		default:
			fmt.Println("working")
		}
	}
}

func main() {
	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)
	fmt.Println("当前时间: ", time.Now().Unix())
	fmt.Println("超时时间: 5秒")
	go doTimeOutStuff(ctx)

	time.Sleep(10 * time.Second)

}
