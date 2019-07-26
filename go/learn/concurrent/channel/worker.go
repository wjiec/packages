package main

import (
	"fmt"
	"time"
)

type WorkerChannel chan int
type WorkerFunction func(c WorkerChannel)

func workerCreator(f WorkerFunction) WorkerChannel {
	c := make(WorkerChannel)
	go f(c)
	return c
}

func main() {
	var workers [100]WorkerChannel
	for i := 0; i < len(workers); i++ {
		workers[i] = workerCreator(func(c WorkerChannel) {
			for {
				fmt.Println(<-c)
			}
		})
	}

	for i := 0; i < len(workers); i++ {
		go func(i int) {
			for j := 0; j < 1000; j++ {
				workers[i] <- i * 10000 + j
			}
		}(i)
	}

	time.Sleep(time.Second)
}
