package queue_any

import (
	"fmt"
	"testing"
)

func TestQueue_Push(t *testing.T) {
	tests := []struct {
		q Queue
		els []int
	} {
		{Queue{}, []int {1, 2, 3}},
	}
	fmt.Println(tests)
}
