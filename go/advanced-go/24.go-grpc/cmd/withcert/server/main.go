package main

import (
	"context"
	"net"

	"google.golang.org/grpc/credentials"

	pb "advanced-go/24.go-grpc/api/echo"
	"google.golang.org/grpc"
)

type EchoService struct{}

func (*EchoService) Echo(_ context.Context, message *pb.Message) (*pb.Message, error) {
	return &pb.Message{Value: "gRPC:" + message.Value}, nil
}

func main() {
	creds, err := credentials.NewServerTLSFromFile("24.go-grpc/config/server.crt", "24.go-grpc/config/server.key")
	if err != nil {
		panic(err)
	}

	g := grpc.NewServer(grpc.Creds(creds))
	pb.RegisterEchoServiceServer(g, new(EchoService))

	l, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	if err := g.Serve(l); err != nil {
		panic(err)
	}
}
