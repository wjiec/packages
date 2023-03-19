package main

import "fmt"

func main() {
	ss := []string{"foo", "bar", "baz"}
	for _, s := range ss {
		go func() {
			fmt.Println(s)
		}()
	}
}
