package conf

import (
	"io/ioutil"
	"sonar-checker/pkg/sonar"

	"gopkg.in/yaml.v2"
)

func LoadRule(filename string) ([]sonar.Rule, error) {
	body, err := ioutil.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	var rules []sonar.Rule
	if err := yaml.Unmarshal(body, &rules); err != nil {
		return nil, err
	}
	return rules, nil
}
