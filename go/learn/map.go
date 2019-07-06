package main

import "fmt"


func main() {
	// map[type_of_key]type_of_value
	var map0 map[string]string
	fmt.Printf("map0 = %v, map0 == nil = %v\n", map0, map0 == nil)

	map1 := map[string]string {
		"a": "abc",
		"b": "bcd",
		"c": "cde",
	}
	fmt.Printf("map1 = %v, len(map1) = %d\n", map1, len(map1))

	// complex map
	map2 := map[string]map[int]float64 {
		"d10": {
			1: .1,
			2: .2,
		},
		"m10": {
			1: 10.,
			2: 20.,
		},
	}
	fmt.Printf("map2 = %v, len(map2) = %d, map2[d10] = %v\n", map2, len(map2), map2["d10"])

	// declare by make
	map3 := make(map[int]int)
	fmt.Printf("map3 = %v, len(map3) = %v, map3 == nil = %v\n", map3, len(map3), map3 == nil)
	map4 := make(map[int]int, 8)
	fmt.Printf("map4 = %v, len(map4) = %v, map4 == nil = %v\n", map4, len(map4), map4 == nil)

	// foreach
	for k, v := range map1 {
		fmt.Printf("map1, %v => %v\n", k, v)
	}

	// getting values
	v1, ok := map2["d10"]
	fmt.Println(v1, ok)
	// zero value
	v2, ok := map2["invalid key"]
	fmt.Println(v2, ok)

	if v, ok := map1["invalid key"]; ok {
		fmt.Println(v)
	} else {
		fmt.Println("Invalid value of key")
	}

	// delete
	va, ok := map1["a"]
	fmt.Println(va, ok)
	delete(map1, "a")
	va, ok = map1["a"]
	fmt.Println(va, ok)
}
