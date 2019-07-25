package main

import (
	"runtime"
	"time"
)

func main() {
	nums := [10]int{}
	for i := 0; i < len(nums); i++ {
		go func(index int) {
			for {
				nums[index] += 1
				// here
				runtime.Gosched()
			}
		}(i)
	}
	time.Sleep(time.Microsecond)
}
