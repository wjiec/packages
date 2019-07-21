package handlers

import (
	"laboys.org/jayson/learn/catcher/wrapper/errors"
	"net/http"
)

func InternalErrorHandler(writer http.ResponseWriter, request *http.Request) error {
	return errors.New(http.StatusInternalServerError)
}

