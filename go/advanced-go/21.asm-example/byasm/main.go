package main

import (
	"fmt"
	"sync"

	"advanced-go/21.asm-example/byasm/tls"
)

func main() {
	var wg sync.WaitGroup
	for i := 0; i < 100; i++ {
		wg.Add(1)
		go func() {
			fmt.Println(*tls.GetId())
			wg.Done()
		}()
	}

	fmt.Println(*tls.GetId())
	wg.Wait()
}
