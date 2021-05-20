package main

import (
	"context"
	"fmt"

	pb "advanced-go/24.go-grpc/api/pubsub"
	"google.golang.org/grpc"
)

func main() {
	conn, err := grpc.Dial("localhost:1234", grpc.WithInsecure())
	if err != nil {
		panic(err)
	}

	c := pb.NewPubSubServiceClient(conn)
	stream, err := c.Subscribe(context.Background(), &pb.Topic{Value: "a"})
	if err != nil {
		panic(err)
	}

	for {
		v, err := stream.Recv()
		if err != nil {
			panic(err)
		}
		fmt.Printf("received: %s\n", v.Value)
	}
}
