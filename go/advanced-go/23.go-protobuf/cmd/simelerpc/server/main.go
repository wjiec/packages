package main

import (
	"log"
	"net"
	"net/rpc"

	"advanced-go/23.go-protobuf/api/echo"
	"advanced-go/23.go-protobuf/internal/simplerpc"
)

type EchoService struct{}

//goland:noinspection GoVetCopyLock
func (*EchoService) Echo(req echo.Message, resp *echo.Message) error {
	resp.Value = "Protobuf:" + req.Value
	return nil
}

func main() {
	err := simplerpc.RegisterEchoServiceServer(new(EchoService))
	if err != nil {
		panic(err)
	}

	l, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	log.Println("server listening in :1234")
	for {
		conn, err := l.Accept()
		if err != nil {
			log.Fatalf("accept: %s\n", err)
		}

		go rpc.ServeConn(conn)
	}
}
