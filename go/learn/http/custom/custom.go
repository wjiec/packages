package main

import (
	bytes2 "bytes"
	"fmt"
	"net/http"
	"net/http/httputil"
	"net/url"
)

func main() {
	client := http.Client{
		Transport: &http.Transport{
			Proxy: func(request *http.Request) (u *url.URL, e error) {
				return url.Parse("http://localhost:10800")
			},
		},
		CheckRedirect: func(req *http.Request, via []*http.Request) error {
			fmt.Printf("<Redirect To=%s, From=[%s]>", req.URL, via[0].URL)
			return nil
		},
	}

	request, e := http.NewRequest(http.MethodGet, "https://google.com", nil)
	if e != nil {
		panic(e)
	}

	response, e := client.Do(request)
	if e != nil {
		panic(e)
	}

	defer func() {
		e := response.Body.Close()
		if e != nil {
			panic(e)
		}
	}()

	bytes, err := httputil.DumpResponse(response, true)
	if err != nil {
		panic(err)
	}

	segments := bytes2.SplitAfterN(bytes, []byte("\r\n"), 2)
	header, body := segments[0], segments[1]
	fmt.Printf("%s %s\n", header, body)
}
