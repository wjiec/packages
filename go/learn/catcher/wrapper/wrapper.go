package main

import (
	"fmt"
	"laboys.org/jayson/learn/catcher/wrapper/handlers"
	"net/http"
)

func main() {
	http.HandleFunc("/success/", handlers.HandlerWrapper(handlers.SuccessHandler))
	http.HandleFunc("/notfound/", handlers.HandlerWrapper(handlers.NotFoundHandler))
	http.HandleFunc("/error/", handlers.HandlerWrapper(handlers.InternalErrorHandler))
	http.HandleFunc("/forbidden/", handlers.HandlerWrapper(handlers.ForbiddenHandler))

	serve := http.ListenAndServe(":8898", nil)
	fmt.Println(serve)
}
