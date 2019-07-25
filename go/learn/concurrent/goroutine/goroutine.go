package main

import (
	"fmt"
	"time"
)

func main() {
	for i := 0; i < 100; i++ {
		go func(id int) {
			for {
				fmt.Printf("goroutine from %d\n", id)
			}
		}(i)
	}
	time.Sleep(4 * time.Second)
}
