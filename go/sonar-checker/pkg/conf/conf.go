package conf

import "time"

type Checker struct {
	Sonar    string
	Username string
	Password string
	Project  string
	Timeout  int64
	Interval int64

	Rules string
}

func (c *Checker) IntervalDuration() time.Duration {
	return time.Duration(c.Interval) * time.Second
}

func (c *Checker) TimeoutDuration() time.Duration {
	return time.Duration(c.Timeout) * time.Second
}
