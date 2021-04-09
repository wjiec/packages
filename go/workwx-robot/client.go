package workrobot

import (
	"bytes"
	"net/http"
	"net/url"

	"go.uber.org/multierr"
)

const (
	// default robot webhook gateway
	DefaultGateway = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send"
)

// Client represents a robot client
type Client struct {
	hc *http.Client

	key     string
	webhook string
}

// Send send messages to the group in order
func (c *Client) Send(messages ...Messager) (err error) {
	for _, msg := range messages {
		if e := c.doSend(msg); e != nil {
			err = multierr.Append(err, e)
		}
	}
	return
}

// doSend send single message to the group
func (c *Client) doSend(msg Messager) error {
	resp, err := c.hc.Post(c.webhook, "application/json", bytes.NewReader(msg.Message()))
	if err != nil {
		return err
	}
	defer func() { _ = resp.Body.Close() }()

	return nil
}

// Option represents additional robot configuration
type Option func(*Client) error

// WithHttpClient override http client for robot request
func WithHttpClient(hc *http.Client) Option {
	return func(client *Client) error {
		client.hc = hc
		return nil
	}
}

// WithWebhook override robot webhook address
func WithWebhook(webhook string) Option {
	return func(client *Client) error {
		api, err := url.Parse(webhook)
		if err != nil {
			return err
		}

		client.webhook = api.String()
		return nil
	}
}

// New create a instance of robot
func New(key string, options ...Option) (*Client, error) {
	c := &Client{hc: http.DefaultClient, key: key}
	for _, opt := range options {
		if err := opt(c); err != nil {
			return nil, err
		}
	}
	return c, nil
}

// Webhook build webhook address from robot key
func Webhook(key string) string {
	api, _ := url.Parse(DefaultGateway)

	q := api.Query()
	q.Add("key", key)

	api.RawQuery = q.Encode()
	return api.String()
}
