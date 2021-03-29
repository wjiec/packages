package main

import "fmt"

func TrimSpace(s []byte) []byte {
	b := s[:0] // len=0 slices
	fmt.Println(b, len(b), cap(b))
	for _, c := range s {
		if c != ' ' {
			b = append(b, c)
		}
	}
	return b
}

func main() {
	var s = []int{1, 2, 3, 4, 5, 6, 7}
	fmt.Println(s, len(s), cap(s))

	var ss = s[1:3]
	fmt.Println(ss, len(ss), cap(ss))
	fmt.Println()

	s = s[:len(s)-1] // delete 1 elements from tail
	fmt.Println(s, len(s), cap(s))
	fmt.Println(ss, len(ss), cap(ss))
	fmt.Println()

	s = append(s[:0], s[2:]...) // delete 2 elements from head
	fmt.Println(s, len(s), cap(s))
	fmt.Println(ss, len(ss), cap(ss))
	fmt.Println()

	s = s[:copy(s, s[1:])] // delete 1 elements from head
	fmt.Println(s, len(s), cap(s))
	fmt.Println(ss, len(ss), cap(ss))
	fmt.Println()

	bs := []byte("  hello world  ")
	tbs := TrimSpace(bs)
	fmt.Printf("%s %d %d\n", tbs, len(tbs), cap(tbs))
}
