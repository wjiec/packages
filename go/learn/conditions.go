package main

import (
	"fmt"
	"io/ioutil"
)

func branch1(score int) int {
	if score < 0 {
		return 0
	} else if score > 100 {
		return 100
	} else {
		return score
	}
}

func branch2(filename string) []byte {
	if contents, err := ioutil.ReadFile(filename); err != nil {
		return []byte{0}
	} else {
		return contents
	}
}

func branch3(score int) string {
	var level string
	switch /* true */ {
	case score < 0 || score > 100:
		panic(fmt.Sprintf("Wrong score: %d", score))
	case score < 60:
		level = "F"
		/* break */
	case score < 70:
		level = "D"
	case score < 80:
		level = "C"
	case score < 90:
		level = "B"
	case score <= 100:
		level = "A"
	}

	return level
}

func main() {
	fmt.Println(branch1(-1))
	fmt.Println(branch1(50))
	fmt.Println(branch1(101))
	fmt.Println(branch2("abc.txt"))
	fmt.Println(branch2("conditions.go"))
	fmt.Println(branch3(0), branch3(59), branch3(100), branch3(101))
}
