package main

/*
#include <stdio.h>

struct _node_t {
    int type;
    float value;
};

static void verify(struct _node_t *node) {
	printf("C: %d %f", node->type, node->value);
}
*/
import "C"
import "fmt"

func main() {
	var node C.struct__node_t
	fmt.Println(node._type)
	fmt.Println(node.value)

	node._type = 123
	node.value = 9.99
	C.verify(&node)
}
