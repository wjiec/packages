package main

import "fmt"

func main() {
	v := 101
	for v >= 0 {
		if v%2 == 0 {
			v := v / 2
			fmt.Println(v)
		} else {
			v := v/3 + 1
			fmt.Println(v)
		}
	}
}
