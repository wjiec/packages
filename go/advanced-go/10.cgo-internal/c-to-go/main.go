package main

// double sum(int a, float b);
import "C"

//export sum
func sum(a C.int, b C.float) C.double {
	return C.double(a) + C.double(b)
}

func main() {}
