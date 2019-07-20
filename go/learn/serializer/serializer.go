package main

import (
	"fmt"
	"laboys.org/jayson/learn/serializer/xml"

	"laboys.org/jayson/learn/serializer/json"
)

type Parser interface {
	Parse(s string) map[interface{}]interface{}
}

type Dumper interface {
	Dump(m *map[interface{}]interface{}) string
}

type Serializer interface {
	Dumper
	Parser
	fmt.Stringer
}

func dumpData(m *map[interface{}]interface{}, d Dumper) string {
	return d.Dump(m)
}

func parseData(s string, parser Parser) map[interface{}]interface{} {
	return parser.Parse(s)
}

func main() {
	data := map[interface{}]interface{} {
		"letter_a": 95,
		"letter_b": 96,
		"letter_c": 97,
	}

	fmt.Println(dumpData(&data, &json.Dumper{}))
	fmt.Println(dumpData(&data, &xml.Dumper{}))

	s1 := `{"letter_c":"97","letter_a":"95","letter_b":"96"}`
	m := map[string]string {}
	for k, v := range parseData(s1, &json.Parser{}) {
		m[k.(string)] = v.(string)
	}
	fmt.Println(m)

	s2 := "<xml></xml>"
	m1 := map[string]int {}
	for k, v := range parseData(s2, &xml.Parser{}) {
		m1[k.(string)] = v.(int)
	}
	fmt.Println(m1)

	var s Serializer
	s = &json.Serializer{}
	fmt.Println(s)

	m2 := s.Dump(&map[interface{}]interface{}{"asd": "asd"})
	o := s.Parse(m2)
	fmt.Println(m1, o)
}
