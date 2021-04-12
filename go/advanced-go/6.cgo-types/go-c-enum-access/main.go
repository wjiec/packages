package main

/*
enum word_t {
	XXX, YYY, ZZZ
};
*/
import "C"
import "fmt"

func main() {
	fmt.Printf("Go: %T -> %v\n", C.XXX, C.XXX)

	var word C.enum_word_t
	word = C.ZZZ
	fmt.Printf("Go: %T -> %v\n", word, word)
}
