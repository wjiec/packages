package main

/*
#cgo windows CFLAGS: -D__OS_WINDOWS__=1
#cgo linux CFLAGS: -D__OS_LINUX__=1

#if defined(__OS_WINDOWS__)
static const char *os = "windows";
#elif defined(__OS_LINUX__)
static const char *os = "linux";
#else
static const char *os = "unknown";
#endif

static const char *plaform() {
	return os;
}
*/
import "C"
import "fmt"

func main() {
	fmt.Println(C.GoString(C.plaform()))
}
