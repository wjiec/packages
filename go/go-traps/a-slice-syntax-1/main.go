package main

import "fmt"

func DumpSlice(s []int) {
	fmt.Printf("%v, len = %d, cap = %d\n", s, len(s), cap(s))
}

func main() {
	s := []int{0, 1, 2, 3, 4, 5, 6, 7}
	DumpSlice(s[:])
	DumpSlice(s[1:])
	fmt.Println()

	DumpSlice(s[:0])
	DumpSlice(s[7:])
	DumpSlice(s[8:])
	fmt.Println()

	DumpSlice(s[:0:0])
	DumpSlice(s[:1:2])
	fmt.Println()

	DumpSlice(s[5:7:7])
	DumpSlice(s[:8:8])
	fmt.Println()

	DumpSlice(s[6:7:8])
}
