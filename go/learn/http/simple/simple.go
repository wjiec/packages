package main

import (
	bytes2 "bytes"
	"fmt"
	"net/http"
	"net/http/httputil"
)

func main() {
	resp, err := http.Get("http://httpbin.org/get")
	if err != nil {
		panic(err)
	}

	defer func() {
		err := resp.Body.Close()
		if err != nil {
			panic(err)
		}
	}()

	bytes, err := httputil.DumpResponse(resp, true)
	if err != nil {
		panic(err)
	}

	segments := bytes2.SplitAfterN(bytes, []byte("\r\n"), 2)
	header, body := segments[0], segments[1]
	fmt.Printf("%s %s\n", header, body)
}
