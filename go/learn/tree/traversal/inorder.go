package traversal

import (
	"fmt"
	"laboys.org/jayson/learn/tree"
)

func InorderTraverse(node *tree.Node) {
	if node == nil {
		return
	}

	InorderTraverse(node.GetLeft())
	fmt.Print(node.GetValue(), " ")
	InorderTraverse(node.GetRight())
}

// Cannot define new methods on non-local type "tree.Node"
//func (node *tree.Node) PreorderTraverse() {
//
//}
