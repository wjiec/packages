package person

/*
#include "person_c_api.h"
*/
import "C"
import (
	"fmt"

	"advanced-go/13.cgo-with-cpp/go-to-cpp/mem"
)

//export cNewPerson
func cNewPerson(name *C.char, age C.int) C.RefAddr {
	return C.RefAddr(mem.Ref(New(C.GoString(name), int(age))))
}

//export cDeletePerson
func cDeletePerson(addr C.RefAddr) {
	mem.Unref(mem.Addr(addr))
}

//export cPersonGetName
func cPersonGetName(addr C.RefAddr) *C.char {
	p := mem.Get(mem.Addr(addr))
	if p != nil {
		return C.CString(p.(*Person).Name())
	}

	return C.CString("")
}

//export cPersonGetAge
func cPersonGetAge(addr C.RefAddr) C.int {
	p := mem.Get(mem.Addr(addr))
	if p != nil {
		return C.int(p.(*Person).Age())
	}

	return C.int(0)
}

//export cPersonSetName
func cPersonSetName(addr C.RefAddr, name *C.char) {
	p := mem.Get(mem.Addr(addr))
	if p != nil {
		p.(*Person).SetName(C.GoString(name))
	}
}

//export cPersonSetAge
func cPersonSetAge(addr C.RefAddr, age C.int) {
	p := mem.Get(mem.Addr(addr))
	if p != nil {
		p.(*Person).SetAge(int(age))
	}
}

func TestPerson() {
	C.testPerson1()
	p1 := mem.Get(mem.Addr(0))
	fmt.Printf("%T %v\n", p1, p1)

	C.testPerson2()
	p2 := mem.Get(mem.Addr(0))
	fmt.Printf("%T %v\n", p2, p2)

	p3 := mem.Get(mem.Addr(0)).(*Person)
	p3.SetName("hello from cgo")
	p3.SetAge(25)
	C.testPerson3()

	C.testPerson4()
	p4 := mem.Get(mem.Addr(0))
	fmt.Printf("%T %v\n", p4, p4)
}
