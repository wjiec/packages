package main

import (
	"errors"
	"fmt"
)

func main() {
	panic(errors.New("RecoveryError by before"))

	defer func() {
		err := recover()
		fmt.Println("defer recover(): ", err)
	}()
}
