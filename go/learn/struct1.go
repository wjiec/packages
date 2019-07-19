package main

import "fmt"

type TreeNode struct {
	value       int
	left, right *TreeNode
}

// method of TreeNode struct
func (node TreeNode) print() {
	fmt.Print(node.value)
}

func (node TreeNode) setValue1(value int) {
	node.value = value
}

func (node *TreeNode) setValue2(value int) {
	node.value = value
}

func main() {
	var node1 TreeNode
	node1.value = 1
	fmt.Println(node1)

	node2 := TreeNode{value: 2}
	fmt.Println(node2)

	// pointer
	node3 := new(TreeNode)
	//node3.left.left.left.left.left = new(TreeNode)
	fmt.Println(node3)

	nodes1 := []TreeNode{
		{value: 1, left: &node1},
		{2, nil, &node2},
		*new(TreeNode),
	}
	fmt.Println(nodes1)

	node1.print()
	fmt.Println()

	node1.setValue1(100)
	node1.print()
	fmt.Println()

	node1.setValue2(100)
	node1.print()
	fmt.Println()
	node1.left.setValue2(200)
	fmt.Println(node1.left)
}
