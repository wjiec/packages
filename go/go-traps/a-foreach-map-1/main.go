package main

import "fmt"

func main() {
	m := map[string]int{"a": 0, "b": 1, "c": 2}
	for v := range m {
		fmt.Println(v)
	}
}
