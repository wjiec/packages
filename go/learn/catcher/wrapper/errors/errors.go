package errors

import "net/http"

type ServerError interface {
	error
}

type HttpError struct {
	Code int
}

func (e *HttpError) Error() string {
	return http.StatusText(e.Code)
}

func New(code int) error {
	return &HttpError{Code: code}
}
