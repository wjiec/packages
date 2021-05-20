package main

import (
	"context"
	"crypto/tls"
	"crypto/x509"
	"errors"
	"fmt"
	"io/ioutil"

	"google.golang.org/grpc/credentials"

	pb "advanced-go/24.go-grpc/api/echo"
	"google.golang.org/grpc"
)

func main() {
	cert, err := tls.LoadX509KeyPair("24.go-grpc/config/client-ca.crt", "24.go-grpc/config/client.key")
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
		ServerName:   "server.com",
		RootCAs:      certPool,
	})

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
