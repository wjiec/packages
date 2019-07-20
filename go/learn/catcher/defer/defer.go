package main

import (
	"bufio"
	"fmt"
	"os"
)

type FibGenerator func() int

func Fib() FibGenerator {
	p, n := 0, 1
	return func() int {
		p, n = n, p + n
		return p
	}
}


func deferCase() {
	// stack call
	defer fmt.Println(1)
	defer fmt.Println(2)
	fmt.Println(3)
}

func writeFib(filename string)  {
	file, err := os.Create(filename)
	if err != nil {
		panic(err)
	}
	defer func() {
		if err := file.Close(); err != nil {
			panic(err)
		}
	}()

	writer := bufio.NewWriter(file)
	defer func() {
		if err := writer.Flush(); err != nil {
			panic(err)
		}
	}()

	generator := Fib()
	for i := 0; i < 20; i++ {
		_, _ = fmt.Fprint(writer, generator())
	}
}

func main() {
	deferCase()
	writeFib("fib.txt")
}
