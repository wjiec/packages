package main

import (
	"fmt"
	"laboys.org/jayson/learn/downloader/http"
)

type Downloader interface {
	Download(url string) string
}

func main() {
	var downloader Downloader = http.Downloader{}
	html := downloader.Download("http://www.httpbin.org/get")
	fmt.Println(html)
}
