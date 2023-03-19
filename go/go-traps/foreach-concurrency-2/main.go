package main

import (
	"fmt"
	"sync"
)

func main() {
	ss := []string{"foo", "bar", "baz"}

	var wg sync.WaitGroup
	for _, s := range ss {
		wg.Add(1)

		go func() {
			fmt.Println(s)
			wg.Done()
		}()
	}
	wg.Wait()
}
