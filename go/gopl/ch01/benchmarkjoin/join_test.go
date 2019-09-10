package benchmarkjoin

import (
	"strings"
	"testing"
)

func prepareSliceOfString() []string {
	var array []string
	for i := 1; i < 128; i++ {
		array = append(array, "A")
	}
	return array
}

func BenchmarkJoin(b *testing.B) {
	a := prepareSliceOfString()
	b.ResetTimer()

	for i := 0; i < b.N; i++ {
		Join(a, " ")
	}
}

func BenchmarkStringsJoin(b *testing.B) {
	a := prepareSliceOfString()
	b.ResetTimer()

	for i := 0; i < b.N; i++ {
		strings.Join(a, " ")
	}
}
