package main

import "fmt"

func appendEnd(ns []int) {
	ns = append(ns, len(ns))
	fmt.Println(ns)
}

func main() {
	ns := []int{1, 2, 3}
	appendEnd(ns)

	fmt.Println(ns)
}
