package main

import (
	"bufio"
	"fmt"
	"os"
)

func hasDuplicateLines(filename string) (map[string]int, bool) {
	if fp, err := os.Open(filename); err == nil {
		counts := make(map[string]int)
		for scanner := bufio.NewScanner(fp); scanner.Scan(); {
			counts[scanner.Text()] += 1
		}

		for _, count := range counts {
			if count >= 2 {
				return counts, true
			}
		}
	}
	return nil, false
}

func main() {
	for _, filename := range os.Args[1:] {
		if counts, ok := hasDuplicateLines(filename); ok {
			fmt.Println(filename)
			for line, count := range counts {
				fmt.Printf("\t%d %s\n", count, line)
			}
		}
	}
}
