package main

import "fmt"

type value struct {
	store map[string]struct{}
}

func main() {
	v := value{}
	if v, ok := v.store["h"]; ok {
		fmt.Println(v, ok)
	}
	fmt.Println("err")
}
