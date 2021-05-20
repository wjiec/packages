package main

import (
	"context"
	"fmt"
	"math/rand"
	"strings"
	"time"

	pb "advanced-go/24.go-grpc/api/pubsub"
	"google.golang.org/grpc"
)

func main() {
	conn, err := grpc.Dial("localhost:1234", grpc.WithInsecure())
	if err != nil {
		panic(err)
	}

	c := pb.NewPubSubServiceClient(conn)
	ctx, cancel := context.WithCancel(context.Background())
	go func() {
		for {
			select {
			case <-ctx.Done():
				return
			case <-time.After(time.Second):
				v := randomString()
				fmt.Printf("publish: %s\n", v)
				if _, err := c.Publish(ctx, &pb.Topic{Value: v}); err != nil {
					fmt.Println(err)
					return
				}
			}
		}
	}()

	time.Sleep(time.Hour)
	cancel()
}

var chars []string

func init() {
	chars = strings.Split("abcdefghijklmnopqrstuvwxyz", "")
	rand.Seed(time.Now().Unix())
}

func randomString() (s string) {
	for i := 0; i < 16; i++ {
		s += chars[rand.Int31n(int32(len(chars)))]
	}
	return
}
