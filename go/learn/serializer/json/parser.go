package json


type Parser struct {}

func (p *Parser) Parse(s string) map[interface{}]interface{} {
	m := map[interface{}]interface{} {}
	var key, value string
	for i := 0; i < len(s); i++ {
		if s[i] == '"' {
			var dst *string
			if len(key) == 0 {
				dst = &key
			} else {
				dst = &value
			}

			for j := i + 1; j < len(s) && s[j] != '"'; j++ {
				*dst += string(s[j])
				i = j + 1
			}
		} else if s[i] == ',' || s[i] == '}' {
			m[key] = value
			key = ""
			value = ""
		}
	}
	return m
}