package main

import (
	"fmt"
	"time"
)

func main() {
	works := []func(){
		func() {
			fmt.Println("sleep 1")
			time.Sleep(time.Second)
		},
		func() {
			fmt.Println("sleep 2")
			time.Sleep(2 * time.Second)
		},
		func() {
			fmt.Println("sleep 3")
			time.Sleep(3 * time.Second)
		},
		func() {
			fmt.Println("sleep 4")
			time.Sleep(4 * time.Second)
		},
		func() {
			fmt.Println("sleep 5")
			time.Sleep(5 * time.Second)
		},
	}
	limiter := make(chan struct{}, 2)
	go func() {
		for _, work := range works {
			go func(w func()) {
				limiter <- struct{}{}
				w()
				<-limiter
			}(work)
		}
	}()

	// block
	select {}
}
