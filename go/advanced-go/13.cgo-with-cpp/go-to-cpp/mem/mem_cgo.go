package mem

/*
#include "mem_c_api.h"
*/
import "C"

//export unref
func unref(addr C.RefAddr) {
	Unref(Addr(addr))
}
