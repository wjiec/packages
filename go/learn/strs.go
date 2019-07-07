package main

import (
	"fmt"
	"unicode/utf8"
)

func main() {
	str1 := "Hello World!!!你好世界!!!"
	for i, ch := range []byte(str1) {
		fmt.Printf("(%d, %X) ", i, ch)
	}
	fmt.Println()

	for i, ch := range str1 {
		fmt.Printf("(%d, %X) ", i, ch)
	}
	fmt.Println()

	bytes1 := []byte(str1)
	fmt.Println(utf8.RuneCount(bytes1))

	for len(bytes1) > 0 {
		ch, size := utf8.DecodeRune(bytes1)
		fmt.Printf("(%X, %c) ", ch, ch)
		bytes1 = bytes1[size:]
	}
	fmt.Println()
}
