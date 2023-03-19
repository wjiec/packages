package main

import "fmt"

func main() {
	ss := []string{"foo", "bar", "baz"}
	for s := range ss {
		fmt.Println(s)
	}
}
