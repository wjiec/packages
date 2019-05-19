package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func toBin(n int) string {
	if n == 0 {
		return "0"
	}

	var result string
	for ; n > 0; n /= 2 {
		result = strconv.Itoa(n % 2) + result
	}
	return result
}

func printFile(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}

	scanner := bufio.NewScanner(file)
	for /* i := 0; */ scanner.Scan() /* ; i += 1 */ {
		fmt.Println(scanner.Text())
	}
}

func main() {
	fmt.Println(
		toBin(0),
		toBin(3),
		toBin(13),
		toBin(123897),
		toBin(12893928),
		toBin(19028312730),
	)
	printFile("loop.go")
}
