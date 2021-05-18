package main

import (
	"context"
	"fmt"

	pb "advanced-go/24.go-grpc/api/echo"
	"google.golang.org/grpc"
)

func main() {
	conn, err := grpc.Dial("localhost:1234", grpc.WithInsecure())
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
