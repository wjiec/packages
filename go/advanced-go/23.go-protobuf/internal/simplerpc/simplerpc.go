package simplerpc

import (
	"net/rpc"

	"advanced-go/23.go-protobuf/api/echo"
)

const serviceName = "simple/echo.EchoService"

type EchoService interface {
	Echo(echo.Message, *echo.Message) error
}

func RegisterEchoServiceServer(service EchoService) error {
	return rpc.RegisterName(serviceName, service)
}

type EchoServiceClient struct {
	c *rpc.Client
}

//goland:noinspection GoVetCopyLock
func (srv *EchoServiceClient) Echo(args echo.Message, reply *echo.Message) error {
	return srv.c.Call(serviceName+".Echo", args, reply)
}

func DialEchoServiceClient(network, address string) EchoService {
	c, err := rpc.Dial(network, address)
	if err != nil {
		panic(err)
	}
	return &EchoServiceClient{c: c}
}
