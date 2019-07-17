package http

import (
	"net/http"
	"net/http/httputil"
)

type Downloader struct {}

func (r Downloader) Download(url string) string {
	resp, err := http.Get(url)
	if err != nil {
		panic(err)
	}

	defer resp.Body.Close()
	bytes, err := httputil.DumpResponse(resp, true)
	if err != nil {
		panic(err)
	}
	return string(bytes)
}



