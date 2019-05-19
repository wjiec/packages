package main

import "fmt"

func main() {
	const (
		cpp = iota
		php
		python
		golang
		_
		javascript
	)
	fmt.Println(cpp, php, python, golang, javascript)

	const (
		b = 1 << (10 * iota)
		kb
		mb
		gb
		tb
		pb
	)
	fmt.Println(b, kb, mb, gb, tb, pb)
}
