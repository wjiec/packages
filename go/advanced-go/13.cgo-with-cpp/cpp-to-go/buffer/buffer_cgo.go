package buffer

/*
#include "buffer_c_api.h"
*/
import "C"
import "unsafe"

type cBuffer C.CBuffer

func cNewBuffer(sz int) cBuffer {
	return cBuffer(C.newBuffer(C.size_t(sz)))
}

func cDeleteBuffer(buf cBuffer) {
	C.deleteBuffer(buf)
}

func cBufferSize(buf cBuffer) int {
	return int(C.bufferSize(buf))
}

func cBufferData(buf cBuffer) []byte {
	sz := cBufferSize(buf)
	return ((*[1 << 32]byte)(unsafe.Pointer(C.bufferData(buf))))[0:sz:sz]
}

func cPrintBuffer(buf cBuffer) {
	C.printBuffer(buf)
}
