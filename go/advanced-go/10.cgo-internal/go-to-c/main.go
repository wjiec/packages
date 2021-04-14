package main

/*
static double sum(int a, float b) {
	return (double) a + (double) b;
}
*/
import "C"
import "fmt"

func main() {
	fmt.Println(C.sum(1, 2.0))
}
