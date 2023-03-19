package main

import (
	"fmt"
	"sync"
)

func main() {
	m := make(map[int]int)

	var wg sync.WaitGroup
	wg.Add(2)

	go func() {
		defer wg.Done()

		for i := 0; i < 100; i += 2 {
			m[i] = i
		}
	}()

	go func() {
		defer wg.Done()

		for i := 1; i < 100; i += 2 {
			m[i] = i
		}
	}()

	wg.Wait()
	fmt.Println(m)
}
