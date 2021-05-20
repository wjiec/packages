package main

import (
	"context"
	"net"
	"strings"
	"time"

	pb "advanced-go/24.go-grpc/api/pubsub"
	"advanced-go/24.go-grpc/pkg/docker/pubsub"
	"google.golang.org/grpc"
)

type PubSubService struct {
	pub *pubsub.Publisher
}

func (p *PubSubService) Publish(_ context.Context, topic *pb.Topic) (*pb.Topic, error) {
	p.pub.Publish(topic.Value)
	return &pb.Topic{Value: topic.Value}, nil
}

func (p *PubSubService) Subscribe(topic *pb.Topic, server pb.PubSubService_SubscribeServer) error {
	ch := p.pub.SubscribeTopic(func(v interface{}) bool {
		return strings.Contains(v.(string), topic.Value)
	})
	defer p.pub.Evict(ch)

	for v := range ch {
		if err := server.Send(&pb.Topic{Value: v.(string)}); err != nil {
			return err
		}
	}
	return nil
}

func main() {
	g := grpc.NewServer()
	pb.RegisterPubSubServiceServer(g, &PubSubService{
		pub: pubsub.NewPublisher(time.Minute, 1024),
	})

	l, err := net.Listen("tcp", ":1234")
	if err != nil {
		panic(err)
	}

	if err := g.Serve(l); err != nil {
		panic(err)
	}
}
