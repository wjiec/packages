package workrobot

import (
	"net/http"
	"testing"
)

func TestWithHttpClient(t *testing.T) {
	httpClient := &http.Client{}
	c, _ := New(Webhook("test-case"), WithHttpClient(httpClient))

	if c.hc != httpClient {
		t.Errorf("unexpected http-client")
	}
}

func TestWithWebhook(t *testing.T) {
	webhook := "https://work.example.com/send?key=test-case"
	c, err := New("", WithWebhook(webhook))
	if err != nil {
		t.Fatal(err)
	}

	if c.webhook != webhook {
		t.Errorf("unexpected webhook")
	}
}
