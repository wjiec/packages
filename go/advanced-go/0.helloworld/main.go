package main

import (
	"fmt"
	"log"
	"net/http"
	"time"
)

func main() {
	http.HandleFunc("/", func(writer http.ResponseWriter, _ *http.Request) {
		_, _ = fmt.Fprintf(writer, "Hello world! -- %v", time.Now())
	})

	if err := http.ListenAndServe(":12345", nil); err != nil {
		log.Fatal(err)
	}
}
