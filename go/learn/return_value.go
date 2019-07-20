package main

import "fmt"

func returnValue(i int) (v int) {
	if i % 2 == 1 {
		v = 1
	} else {
		v = 0
	}
	return
}


func main() {
	fmt.Println(returnValue(0))
	fmt.Println(returnValue(1))
	fmt.Println(returnValue(2))
}
