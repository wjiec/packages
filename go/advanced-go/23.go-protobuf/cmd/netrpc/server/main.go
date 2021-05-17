package main

import (
	"net"
	"net/rpc"

	echo "advanced-go/23.go-protobuf/api/netecho"
)

type EchoService struct{}

func (*EchoService) Echo(req echo.Message, resp *echo.Message) error {
	resp.Value = "NetRPC:" + req.Value
	return nil
}

func main() {
	srv := rpc.NewServer()
	if err := echo.RegisterEchoService(srv, new(EchoService)); err != nil {
		panic(err)
	}

	l, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	srv.Accept(l)
}
