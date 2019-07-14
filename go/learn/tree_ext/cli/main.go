package main

import (
	"laboys.org/jayson/learn/tree"
	"laboys.org/jayson/learn/tree_ext"
)

func main() {
	//         0
	//     1       2
	//  3     4
	//
	root := tree.Node{Value: 0}
	root.Left = &tree.Node{Value:1}
	root.Right = &tree.Node{Value:2}
	root.Left.Left = &tree.Node{Value:3}
	root.Left.Right = &tree.Node{Value:4}

	extRoot := tree_ext.Node{Node: &root}
	extRoot.PostOrder()
}
