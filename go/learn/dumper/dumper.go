package main

import (
	"fmt"
	"laboys.org/jayson/learn/dumper/json"
	"laboys.org/jayson/learn/dumper/xml"
)

type Dumper interface {
	Dump(*map[interface{}]interface{}) string
}

func dumpData(m *map[interface{}]interface{}, d Dumper)  {
	fmt.Println(d.Dump(m))
}

func main() {
	data := map[interface{}]interface{} {
		"letter_a": 95,
		"letter_b": 96,
		"letter_c": 97,
	}

	dumpData(&data, &json.Dumper{})
	dumpData(&data, &xml.Dumper{})
}
