package handlers

import "net/http"

func SuccessHandler(writer http.ResponseWriter, request *http.Request) error {
	_, e := writer.Write([]byte("ok"))
	return e
}
