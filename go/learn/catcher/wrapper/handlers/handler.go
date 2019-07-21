package handlers

import (
	"laboys.org/jayson/learn/catcher/wrapper/errors"
	"net/http"
)

type ServerHandler func(writer http.ResponseWriter, request *http.Request) error
type RouteHandler func(writer http.ResponseWriter, request *http.Request)

func HandlerWrapper(handler ServerHandler) RouteHandler {
	return func(writer http.ResponseWriter, request *http.Request) {
		if err := handler(writer, request); err != nil {
			var assertion bool
			var httpError *errors.HttpError
			if httpError, assertion = err.(*errors.HttpError); !assertion {
				httpError = errors.New(http.StatusInternalServerError).(*errors.HttpError)
			}
			http.Error(writer, httpError.Error(), httpError.Code)
		}
	}
}
