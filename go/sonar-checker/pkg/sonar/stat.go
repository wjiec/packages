package sonar

import (
	"errors"
	"fmt"
	"strconv"
)

type Metric struct {
	Component string `json:"component"`
	BestValue bool   `json:"bestValue"`
	Metric    string `json:"metric"`
	Value     string `json:"value"`
}

type stat struct {
	measures map[string]Metric
}

type Rule struct {
	Metric   string `toml:"metric"`
	Operator string `toml:"operator"`
	Value    string `toml:"value"`
}

func (s *stat) Check(rules []Rule) error {
	for _, r := range rules {
		m, ok := s.measures[r.Metric]
		if !ok {
			return errors.New("stat: unknown metric: " + r.Metric)
		}

		if m.BestValue {
			continue
		}

		if !compare(r.Operator, m.Value, r.Value) {
			return errors.New(fmt.Sprintf("stat: compare %s: %s %s %s failure",
				r.Metric, m.Value, r.Operator, r.Value))
		}
	}

	return nil
}

func compare(op string, left, right string) bool {
	var l, r float64
	l, _ = strconv.ParseFloat(left, 64)
	r, _ = strconv.ParseFloat(right, 64)

	switch op {
	case "eq", "=", "==", "===":
		return left == right || l == r
	case "neq", "!=", "!==":
		return l != r
	case "gt", ">":
		return l > r
	case "ge", ">=":
		return l >= r
	case "lt", "<":
		return l < r
	case "le", "<=":
		return l <= r
	}

	return false
}

func NewStat(measures []Metric) *stat {
	s := &stat{measures: make(map[string]Metric)}
	for _, m := range measures {
		s.measures[m.Metric] = m
	}

	return s
}
