package main

import (
	"fmt"
	"sync"
)

var g = 1

func Incr() {
	var lock sync.Mutex
	lock.Lock()
	defer lock.Unlock()

	g++
}

func main() {
	var wg sync.WaitGroup
	for i := 0; i < 10; i++ {
		wg.Add(1)

		go func() {
			defer wg.Done()

			for j := 0; j < 100; j++ {
				Incr()
			}
		}()
	}
	wg.Wait()
	fmt.Println(g)
}
