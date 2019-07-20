package xml


type Parser struct {}

func (p *Parser) Parse(s string) map[interface{}]interface{} {
	return map[interface{}]interface{} {
		"mock_key_1": 1,
		"mock_key_2": 1,
		"mock_key_3": 1,
	}
}
