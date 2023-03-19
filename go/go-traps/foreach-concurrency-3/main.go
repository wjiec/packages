package main

import (
	"fmt"
	"sync"
)

func dump(s string, wg sync.WaitGroup) {
	defer wg.Done()

	fmt.Println(s)
}

func main() {
	ss := []string{"foo", "bar", "baz"}

	var wg sync.WaitGroup
	for _, s := range ss {
		wg.Add(1)
		go dump(s, wg)
	}
	wg.Wait()
}
