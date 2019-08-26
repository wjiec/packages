package main

import (
	"fmt"
	"time"
)

type zombie struct {
	dict map[int]int
}

func newZombie() *zombie {
	z := &zombie{
		dict: make(map[int]int),
	}
	go z.gen()

	return z
}

func (z *zombie) gen() {
	for {
		fmt.Println("zombie alive")
		if v, ok := z.dict[1]; ok {
			z.dict[1] += v
		} else {
			z.dict[1] = 1
		}
		time.Sleep(200 * time.Millisecond)
		fmt.Println(z.dict)
	}
}

func main() {
	z := newZombie()
	fmt.Println(z)

	time.Sleep(time.Second)
	z = nil
	fmt.Println(z)

	time.Sleep(time.Second)
	fmt.Println("main exited")
}
