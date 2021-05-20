package main

import (
	"context"
	"fmt"

	"google.golang.org/grpc/credentials"

	pb "advanced-go/24.go-grpc/api/echo"
	"google.golang.org/grpc"
)

func main() {
	creds, err := credentials.NewClientTLSFromFile("24.go-grpc/config/server.crt", "server.com")
	if err != nil {
		panic(err)
	}

	conn, err := grpc.Dial("localhost:1234", grpc.WithTransportCredentials(creds))
	if err != nil {
		panic(err)
	}

	c := pb.NewEchoServiceClient(conn)
	resp, err := c.Echo(context.Background(), &pb.Message{Value: "Hello from gRPC"})
	if err != nil {
		panic(err)
	}
	fmt.Println(resp)
}
