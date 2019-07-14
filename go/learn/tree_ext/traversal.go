package tree_ext

import "fmt"

func (node *Node) PostOrder() {
	if node == nil || node.Node == nil {
		return
	}

	left := Node{node.Node.GetLeft()}
	left.PostOrder()

	right := Node{node.Node.GetRight()}
	right.PostOrder()

	fmt.Print(node.Node.GetValue(), " ")
}
