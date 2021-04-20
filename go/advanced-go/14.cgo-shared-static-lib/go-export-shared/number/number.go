package number

import "C"

//export addNumber
func addNumber(a, b C.int) C.int {
	return C.int(int(a) + int(b))
}
