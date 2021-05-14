package main

import (
	"io"
	"net/http"
	"net/rpc"
	"net/rpc/jsonrpc"

	"advanced-go/22.go-rpc/simple/api/echo"
)

type EchoService struct{}

func (*EchoService) Echo(s string, v *string) error {
	*v = "HTTP:" + s
	return nil
}

func main() {
	if err := echo.RegisterEchoServiceServer(new(EchoService)); err != nil {
		panic(err)
	}

	http.HandleFunc("/jsonrpc", func(writer http.ResponseWriter, request *http.Request) {
		var conn io.ReadWriteCloser = struct {
			io.Writer
			io.ReadCloser
		}{
			Writer:     writer,
			ReadCloser: request.Body,
		}

		if err := rpc.ServeRequest(jsonrpc.NewServerCodec(conn)); err != nil {
			panic(err)
		}
	})

	if err := http.ListenAndServe(":1234", nil); err != nil {
		panic(err)
	}
}
