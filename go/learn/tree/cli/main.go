package main

import (
	"laboys.org/jayson/learn/tree"
)

func main() {
	//         0
	//     1       2
	//  3     4
	//
	root := tree.Node{Value: 0}
	root.Left = &tree.Node{Value: 1}
	root.Right = &tree.Node{Value: 2}
	root.Left.Left = &tree.Node{Value: 3}
	root.Left.Right = &tree.Node{Value: 4}
	tree.InorderTraverse(&root)
}
