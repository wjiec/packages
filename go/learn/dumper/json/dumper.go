package json

import "fmt"

type Dumper struct{}

func (d *Dumper) Dump(m *map[interface{}]interface{}) string {
	s := "{"
	for k, v := range *m {
		s += fmt.Sprintf("\"%s\":\"%v\",", k.(string), v)
	}
	return s[:len(s) - 1] + "}"
}
