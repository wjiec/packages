package main

import "fmt"

func varByZeroValue() {
	var num1 int
	var str1 string
	fmt.Printf("%d %q %s", num1, str1, str1)

	var num2, str2, b1 = 3, "abc", false
	fmt.Println(num2, str2, b1)

	a, b, c := 1, true, "q"
	fmt.Println(a, b, c)

	var (
		aa = 1
		bb = true
		cc = "11"
	)
	fmt.Println(aa, bb, cc)
}

func main() {
	fmt.Println("Hello World!")
	varByZeroValue()
}
