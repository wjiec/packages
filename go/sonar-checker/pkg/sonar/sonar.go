package sonar

import (
	"context"
	"errors"
	"strconv"
	"time"

	"github.com/guonaihong/gout"
	"github.com/guonaihong/gout/dataflow"
)

type Sonar struct {
	Host     string
	Username string
	Password string

	out *dataflow.Gout
}

func New(host string, username, password string) (*Sonar, error) {
	s := &Sonar{
		Host:     host,
		Username: username,
		Password: password,
		out:      gout.New(),
	}
	return s, s.login()
}

func (s *Sonar) Polling(ctx context.Context, project string) <-chan string {
	ids := make(chan string)
	go func() {
	DONE:
		for {
			select {
			case <-time.After(time.Second):
				if id := s.lastId(project); len(id) != 0 {
					ids <- id
					break DONE
				}
			case <-ctx.Done():
				break DONE
			}
		}
		close(ids)
	}()
	return ids
}

const MetricKeys = "alert_status,bugs,reliability_rating,vulnerabilities,security_rating,security_hotspots_reviewed,security_review_rating,code_smells,sqale_rating,duplicated_lines_density,coverage"

func (s *Sonar) Stat(project string, id string) (*stat, error) {
	var stat struct {
		Measures []Metric `json:"measures"`
	}

	err := s.out.GET(s.Host + "/api/measures/search").SetQuery(gout.H{
		"metricKeys":  MetricKeys,
		"projectKeys": project,
	}).BindJSON(&stat).Do()
	return NewStat(stat.Measures), err
}

func (s *Sonar) login() error {
	var code int
	err := s.out.POST(s.Host + "/api/authentication/login").SetQuery(gout.H{
		"login": s.Username, "password": s.Password,
	}).Code(&code).Do()

	if code != 200 {
		if err != nil {
			return err
		}
		return errors.New("sonar login: " + strconv.FormatInt(int64(code), 10))
	}
	return err
}

type task struct {
	Id     string `json:"id"`
	Status string `json:"status"`
}

type resp struct {
	Queue   []task `json:"queue"`
	Current task   `json:"current"`
}

func (s *Sonar) lastId(project string) string {
	var r resp
	err := s.out.GET(s.Host + "/api/ce/component").SetQuery(gout.H{"component": project}).BindJSON(&r).Do()
	if err != nil {
		return ""
	}

	if len(r.Queue) == 0 {
		return r.Current.Id
	}

	if r.Queue[0].Status == "SUCCESS" {
		return r.Queue[0].Id
	}
	return ""
}
