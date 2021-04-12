package main

/*
#include <stdio.h>
#include <stdint.h>

union byte_order {
	uint32_t value;
	struct _inner_t {
		uint8_t b0;
		uint8_t b1;
		uint8_t b2;
		uint8_t b3;
	} bytes;
};

static void print_byte_order(union byte_order bo) {
	printf("C: int value = %#x\n", bo.value);
	printf("C: b0 = %d, b1 = %d, b2 = %d, b3 = %d\n", bo.bytes.b0, bo.bytes.b1, bo.bytes.b2, bo.bytes.b3);
}

*/
import "C"
import "fmt"

func main() {
	var bo C.union_byte_order
	fmt.Printf("Go: bo => %T(%v)\n", bo, bo)
	bo[0] = 1
	bo[1] = 2
	bo[2] = 3
	bo[3] = 4
	fmt.Printf("Go: bo => %T(%v)\n", bo, bo)

	C.print_byte_order(bo)
}
