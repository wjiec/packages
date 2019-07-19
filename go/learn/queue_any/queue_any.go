package queue_any

type Queue []interface{}

func (q *Queue) Push(v interface{}) *Queue {
	*q = append(*q, v)
	return q
}

func (q *Queue) Pop() interface{} {
	front := (*q)[0]
	*q = (*q)[1:]
	return front
}

func (q *Queue) IsEmpty() bool {
	return len(*q) == 0
}
