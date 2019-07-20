package xml

import "fmt"

type Dumper struct {}

func (d *Dumper) Dump(m *map[interface{}]interface{}) string {
	s := "<xml>"
	for k, v := range *m {
		s += fmt.Sprintf("<%s>%v</%s>", k, v, k)
	}
	return s + "</xml>"
}



