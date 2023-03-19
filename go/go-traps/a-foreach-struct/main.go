package main

import "fmt"

type User struct {
	Name string
	Age  int
}

func main() {
	us := []User{{Name: "foo"}, {Name: "bar"}, {Name: "baz"}}
	for idx, u := range us {
		u.Age = idx
	}
	fmt.Println(us)
}
