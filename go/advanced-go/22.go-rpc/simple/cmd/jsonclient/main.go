package main

import (
	"fmt"

	"advanced-go/22.go-rpc/simple/api/echo"
)

func main() {
	srv := echo.DialRawEchoService("tcp", "localhost:1234", echo.WithJsonCodec())
	var s string
	if err := srv.Echo("hello world", &s); err != nil {
		panic(err)
	}
	fmt.Println(s)

	if err := srv.Echo("hello from rpc", &s); err != nil {
		panic(err)
	}
	fmt.Println(s)
}
