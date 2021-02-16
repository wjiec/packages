package main

import (
	"context"
	"flag"
	"fmt"
	"os"
	"sonar-checker/pkg/conf"
	"sonar-checker/pkg/sonar"
	"time"
)

func Fatal(format string, args ...interface{}) {
	_, _ = fmt.Fprintf(os.Stderr, format+"\n", args...)
	os.Exit(1)
}

var cfg = conf.Checker{}

func init() {
	flag.StringVar(&cfg.Sonar, "sonar", "https://sonarqube.example.com", "sonar host")
	flag.StringVar(&cfg.Sonar, "s", "https://sonarqube.example.com", "sonar host")

	flag.StringVar(&cfg.Username, "username", "__async_checker", "sonar username")
	flag.StringVar(&cfg.Username, "u", "__async_checker", "sonar username")

	flag.StringVar(&cfg.Password, "password", "example.com", "sonar password")
	flag.StringVar(&cfg.Password, "p", "example.com", "sonar password")

	flag.StringVar(&cfg.Project, "key", "", "project key")
	flag.StringVar(&cfg.Project, "k", "", "project key")

	flag.StringVar(&cfg.Rules, "rules", "build/ci/sonar-checker.yml", "project rules")
	flag.StringVar(&cfg.Rules, "r", "build/ci/sonar-checker.yml", "project rules")

	flag.Int64Var(&cfg.Interval, "interval", 1, "check interval")
	flag.Int64Var(&cfg.Timeout, "timeout", 300, "check timeout")
}

func main() {
	flag.Parse()
	if len(cfg.Sonar) == 0 || len(cfg.Project) == 0 {
		Fatal("invalid sonar host or project key")
	}

	if len(cfg.Rules) == 0 {
		Fatal("invalid sonar host or project key")
	}

	rules, err := conf.LoadRule(cfg.Rules)
	if err != nil {
		Fatal("rules: " + err.Error())
	}

	sq, err := sonar.New(cfg.Sonar, cfg.Username, cfg.Password)
	if err != nil {
		Fatal(err.Error())
	}

	ctx, _ := context.WithTimeout(context.Background(), 30*time.Second)
	queue := sq.Polling(ctx, cfg.Project)
	for {
		select {
		case id := <-queue:
			stat, err := sq.Stat(cfg.Project, id)
			if err != nil {
				Fatal("sonar stat: " + err.Error())
			}

			if err := stat.Check(rules); err != nil {
				Fatal("sonar check: " + err.Error())
			}

			os.Exit(0)
		case <-time.After(cfg.TimeoutDuration()):
			Fatal("polling timeout")
		}
	}
}
