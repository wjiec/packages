package main

import (
	"fmt"
	"io"
	"net/http"
	"os"
)

func main() {
	for _, url := range os.Args[1:] {
		resp, err := http.Get(url)
		if err != nil {
			_ = fmt.Errorf("fetch: %v\n", err)
			os.Exit(1)
		}

		if _, err = io.Copy(os.Stdout, resp.Body); err != nil {
			_ = fmt.Errorf("copy: %v\n", err)
		}
		_ = resp.Body.Close()
	}
}
