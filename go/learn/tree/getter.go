package tree

func (node *Node) GetValue() int {
	return node.Value
}

func (node *Node) GetLeft() *Node {
	return node.Left
}

func (node *Node) GetRight() *Node {
	return node.Right
}
