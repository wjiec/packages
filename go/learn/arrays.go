package main

import "fmt"

func passByValue(arr [5]int) {
	for idx, val := range arr {
		idx = idx * idx
		val = val * val
	}

	// not effect
	for idx, val := range arr {
		fmt.Println(idx, val)
	}
}

func main() {
	var array1 [8]int
	fmt.Println(array1)

	array2 := [3]int{1, 2, 3}
	fmt.Println(array2)

	array3 := [...]int{2, 4, 6, 8, 10}
	fmt.Println(array3)

	array4 := [...][3]int{{0, 1, 2}, {0, 1, 2}}
	fmt.Println(array4)

	// bad
	for i := 0; i < len(array2); i++ {
		fmt.Println(i)
	}

	// good
	for index, row := range array4 {
		fmt.Println(index, row)
	}

	// test, pass by value
	passByValue(array3)
	for k, v := range array3 {
		fmt.Println(k, v)
	}

}
