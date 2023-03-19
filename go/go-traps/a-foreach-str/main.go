package main

import "fmt"

func main() {
	s := "hello, 你好"
	for i, c := range s {
		fmt.Printf("%d -> %c\n", i, c)
	}
}
