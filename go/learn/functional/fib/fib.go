package main

import (
	"bufio"
	"fmt"
	"io"
	"strings"
)

type FibGenerator func() int

func (g FibGenerator) Read(p []byte) (n int, err error) {
	return strings.NewReader(fmt.Sprintf("%d\n", g())).Read(p)
}

func Fib() FibGenerator {
	p, n := 0, 1
	return func() int {
		p, n = n, p + n
		return p
	}
}

func echo(reader io.Reader) {
	scanner := bufio.NewScanner(reader)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}

func main() {
	//g := Fib()
	//fmt.Println(g())
	//fmt.Println(g())
	//fmt.Println(g())

	echo(Fib())
}
