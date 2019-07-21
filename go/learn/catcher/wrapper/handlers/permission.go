package handlers

import (
	"laboys.org/jayson/learn/catcher/wrapper/errors"
	"net/http"
)

func ForbiddenHandler(writer http.ResponseWriter, request *http.Request) error {
	return errors.New(http.StatusForbidden)
}
