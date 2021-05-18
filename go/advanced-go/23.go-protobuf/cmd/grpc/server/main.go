package main

import (
	"context"
	"net"

	pb "advanced-go/23.go-protobuf/api/echo"
	"google.golang.org/grpc"
)

type EchoService struct{}

func (*EchoService) Echo(_ context.Context, message *pb.Message) (*pb.Message, error) {
	return &pb.Message{Value: "gRPC:" + message.Value}, nil
}

func main() {
	g := grpc.NewServer()
	pb.RegisterEchoServiceServer(g, new(EchoService))

	l, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	if err := g.Serve(l); err != nil {
		panic(err)
	}
}
