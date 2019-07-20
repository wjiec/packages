package json

import "fmt"

type Serializer struct {
	dumper *Dumper
	parser *Parser
}

func (serializer *Serializer) String() string {
	return fmt.Sprintf("<json.Serializer >")
}

func (serializer *Serializer) Dump(m *map[interface{}]interface{}) string {
	return serializer.dumper.Dump(m)
}

func (serializer *Serializer) Parse(s string) map[interface{}]interface{} {
	return serializer.parser.Parse(s)
}



