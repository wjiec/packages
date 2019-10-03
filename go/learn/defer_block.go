package main

import "fmt"

func main() {
	{
		defer func() {
			fmt.Println("block end")
		}()
	}

	fmt.Println("waiting exit")
}
