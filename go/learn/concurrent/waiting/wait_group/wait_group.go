package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func main() {
	fmt.Println(time.Now())

	var waitGroup sync.WaitGroup
	for i := 0; i < 100; i++ {
		waitGroup.Add(1)
		go func() {
			time.Sleep(time.Duration(rand.Int31n(5)) * time.Second)
			waitGroup.Done()
		}()
	}

	waitGroup.Wait()
	fmt.Println(time.Now())
}
