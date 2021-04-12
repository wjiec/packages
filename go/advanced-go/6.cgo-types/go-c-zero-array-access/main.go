package main

/*
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

struct node_t {
	int32_t hi[2];
	int32_t lo[];
};

static struct node_t *create_node() {
	struct node_t *node = (struct node_t *)malloc(sizeof(struct node_t));
	node->hi[0] = 1;
	node->hi[1] = 2;

	return node;
}

*/
import "C"
import (
	"fmt"
)

func main() {
	var gn C.struct_node_t
	fmt.Println(gn.hi)
	//fmt.Println(gn.lo)
	//fmt.Println(unsafe.Offsetof(gn.lo))
}
