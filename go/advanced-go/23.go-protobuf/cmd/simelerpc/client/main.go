package main

import (
	"fmt"

	"advanced-go/23.go-protobuf/api/echo"
	"advanced-go/23.go-protobuf/internal/simplerpc"
)

func main() {
	srv := simplerpc.DialEchoServiceClient("tcp", "localhost:1234")

	var reply echo.Message
	if err := srv.Echo(echo.Message{Value: "hello from protobuf"}, &reply); err != nil {
		panic(err)
	}
	fmt.Println(reply)
}
