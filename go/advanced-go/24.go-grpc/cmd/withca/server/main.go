package main

import (
	"context"
	"crypto/tls"
	"crypto/x509"
	"errors"
	"io/ioutil"
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
	cert, err := tls.LoadX509KeyPair("24.go-grpc/config/server-ca.crt", "24.go-grpc/config/server.key")
	if err != nil {
		panic(err)
	}

	certPool := x509.NewCertPool()
	ca, err := ioutil.ReadFile("24.go-grpc/config/ca.crt")
	if err != nil {
		panic(err)
	}
	if ok := certPool.AppendCertsFromPEM(ca); !ok {
		panic(errors.New("unable to append ca"))
	}

	creds := credentials.NewTLS(&tls.Config{
		Certificates: []tls.Certificate{cert},
		ClientAuth:   tls.RequireAndVerifyClientCert,
		ClientCAs:    certPool,
	})

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
