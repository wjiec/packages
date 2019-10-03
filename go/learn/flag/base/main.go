package main

import (
	"flag"
	"fmt"
)

func main() {
	w := flag.Bool("w", false, "bool flag: w")
	flag.Parse()

	fmt.Println(*w)
	fmt.Printf("flag.NArgs: %d\n", flag.NArg())
	fmt.Printf("flag.Args: %v\n", flag.Args())
}
