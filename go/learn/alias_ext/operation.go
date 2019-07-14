package alias_ext


func (q *Queue) Push(v int) {
	*q = append(*q, v)
}

func (q *Queue) Pop() int {
	if q.Empty() {
		panic("empty queue")
	}

	front := (*q)[0]
	*q = (*q)[1:]
	return front
}

func (q *Queue) Empty() bool {
	return len(*q) == 0
}

