package main

import (
	"log"
	"net"
	"net/rpc"
	"net/rpc/jsonrpc"

	"advanced-go/22.go-rpc/simple/api/echo"
)

type EchoService struct{}

func (*EchoService) Echo(s string, v *string) error {
	*v = "JSON:" + s
	return nil
}

func main() {
	if err := echo.RegisterEchoServiceServer(new(EchoService)); err != nil {
		panic(err)
	}

	l, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	log.Println("json server listening in :1234")

	for {
		conn, err := l.Accept()
		if err != nil {
			log.Fatalf("accept: %s\n", err)
		}

		go rpc.ServeCodec(jsonrpc.NewServerCodec(conn))
	}
}
