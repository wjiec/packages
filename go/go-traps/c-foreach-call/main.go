package main

type Number int

func (n *Number) Reset() {
	*n = 0
}

func main() {
	m := map[string]Number{"a": 1, "b": 2, "c": 3}
	for k := range m {
		m[k].Reset()
	}
}
