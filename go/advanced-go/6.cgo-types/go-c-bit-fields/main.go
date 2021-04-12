package main

/*
#include <stdio.h>
#include <stdint.h>

struct _node_t {
	uint8_t flags : 6;
	uint8_t syn : 1;
	uint8_t fin : 1;
	uint8_t type;
};

static uint8_t get_flags(const struct _node_t *node) {
	return node->flags;
}

static void set_flags(struct _node_t *node, uint8_t val) {
	node->flags = val;
}
*/
import "C"
import (
	"fmt"
)

func main() {
	var node C.struct__node_t
	fmt.Println(node._type)
	//fmt.Println(node.flag)

	// 11_1111 => 0x3_f
	C.set_flags(&node, 0xff)

	v := C.get_flags(&node)
	fmt.Printf("Go: flags = %#v(%T)\n", v, v)
}
