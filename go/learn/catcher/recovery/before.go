package main

import (
	"errors"
	"fmt"
)

func main() {
	defer func() {
		err := recover()
		fmt.Println("defer recover(): ", err)
	}()

	panic(errors.New("RecoveryError by before"))
}
