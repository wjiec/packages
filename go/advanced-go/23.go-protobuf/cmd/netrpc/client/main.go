package main

import (
	"fmt"

	echo "advanced-go/23.go-protobuf/api/netecho"
)

func main() {
	c, err := echo.DialEchoService("tcp", "localhost:1234")
	if err != nil {
		panic(err)
	}

	var resp echo.Message
	if err := c.Echo(echo.Message{Value: "hello from netrpc"}, &resp); err != nil {
		panic(err)
	}

	fmt.Println("resp: ", resp.Value)
}
