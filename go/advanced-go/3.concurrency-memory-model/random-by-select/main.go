package main

import "fmt"

func main() {
	ch := make(chan bool)
	go func() {
		for i := 0; i < 1000000; i++ {
			select {
			case ch <- true:
			case ch <- false:
			}
		}

		close(ch)
	}()

	data := make(map[bool]int)
	for v := range ch {
		data[v] += 1
	}

	fmt.Println(data)
}
