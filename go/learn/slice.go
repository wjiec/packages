package main

import "fmt"

func passByView(arr []int)  {
	arr[0] = 0xff
}

func main() {
	// test failure
	//autoArray := [1024]int { iota }
	arr1 := [...]int { 0, 1, 2, 3, 4, 5, 6, 7 }
	fmt.Println(arr1[2:4]) // [2, 4)
	fmt.Println(arr1[:4]) // [2, 4)
	fmt.Println(arr1[2:]) // [2, 4)
	fmt.Println(arr1[:]) // [2, 4)

	s1 := arr1[:]
	passByView(s1)
	fmt.Println(s1)

	s2 := arr1[2:4]
	passByView(s2)
	fmt.Println(s2)
	fmt.Println("arr1 =", arr1)

	// re-slice
	fmt.Println(s2[1:])

	// out of range
	//fmt.Println(arr1[4:3]) // error

	fmt.Println("arr1 =", arr1)
	slice1 := arr1[2:4]
	fmt.Println("slice1 =", slice1) // { arr1[2], arr1[3], [arr1[4]], [...] }
	func() {
		defer func() {
			if r := recover(); r != nil {
				fmt.Println(">>> Error:", r)
			}
		}()

		for i := 0; i < 10; i++ {
			fmt.Println(slice1[:i])
		}
	}()

	fmt.Println("arr1 =", arr1)
	fmt.Printf("slice1 = %v, len(slice1) = %d, cap(slice1) = %d\n", slice1, len(slice1), cap(slice1))

	// append slice
	slice2 := arr1[3:7]
	fmt.Println("arr1 =", arr1)
	fmt.Printf("slice2 = %v, len(slice2) = %d, cap(slice2) = %d\n", slice2, len(slice2), cap(slice2))
	append1 := append(slice2, -1)
	append2 := append(append1, -2)
	append3 := append(append2, -3)
	fmt.Printf("append1 = %v, len(append1) = %d, cap(append1) = %d\n", append1, len(append1), cap(append1))
	fmt.Printf("append2 = %v, len(append2) = %d, cap(append2) = %d\n", append2, len(append2), cap(append2))
	fmt.Printf("append3 = %v, len(append3) = %d, cap(append3) = %d\n", append3, len(append3), cap(append3))
	fmt.Printf("arr1 = %v, len(arr1) = %d, cap(arr1) = %d\n", arr1, len(arr1), cap(arr1))

	// directly definition slice
	var slice3 []int
	fmt.Println(slice3, slice3 == nil)

	// append to slice
	for i:= 0; i < 100; i++ {
		slice3 = append(slice3, i)
		fmt.Printf("len(slice3) = %d, cap(slice3) = %d\n", len(slice3), cap(slice3))
	}

	sliceInit := []int {2, 4, 6, 8}
	fmt.Printf("sliceInit = %v, len(sliceInit) = %d, cap(sliceInit) = %d\n", sliceInit, len(sliceInit), cap(sliceInit))

	// make slice
	slice4 := make([]int, 16)
	slice5 := make([]int, 16, 64)
	fmt.Printf("slice4 = %v, len(slice4) = %d, cap(slice4) = %d\n", slice4, len(slice4), cap(slice4))
	fmt.Printf("slice5 = %v, len(slice5) = %d, cap(slice5) = %d\n", slice5, len(slice5), cap(slice5))

	// copy
	fmt.Println(copy(slice4, slice2))
	fmt.Printf("slice4 = %v, len(slice4) = %d, cap(slice4) = %d\n", slice4, len(slice4), cap(slice4))

	// delete
	slice4 = append(slice4[:3], slice4[4:]...)
	fmt.Printf("slice4 = %v, len(slice4) = %d, cap(slice4) = %d\n", slice4, len(slice4), cap(slice4))

	// shift
	front := sliceInit[0]
	sliceInit = sliceInit[1:]
	fmt.Printf("front = %d, sliceInit = %d\n", front, sliceInit)

	// pop
	back := sliceInit[len(sliceInit) - 1]
	sliceInit = sliceInit[:len(sliceInit) - 1]
	fmt.Printf("back = %d, sliceInit = %d\n", back, sliceInit)
}
