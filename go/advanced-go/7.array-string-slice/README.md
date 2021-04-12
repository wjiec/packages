CGO编程 - 数组、字符串和切片
-----------------------

在C语言中，数组名其实对应一个指针，指向特定类型特定长度的一段内存，但是这个指针不能修改。

Go语言字符串对应一段长度确定的只读byte类型的内存。Go语言中的切片则是一个简化版的动态数组。

CGO的虚拟C包中提供了一组函数，用于Go语言和C语言之间数组和字符串类型的双向转换：
```cgo
// 
// src/cmd/cgo/doc.go
//
/*

A few special functions convert between Go and C types
by making copies of the data. In pseudo-Go definitions:

	// Go string to C string
	// The C string is allocated in the C heap using malloc.
	// It is the caller's responsibility to arrange for it to be
	// freed, such as by calling C.free (be sure to include stdlib.h
	// if C.free is needed).
	func C.CString(string) *C.char

	// Go []byte slice to C array
	// The C array is allocated in the C heap using malloc.
	// It is the caller's responsibility to arrange for it to be
	// freed, such as by calling C.free (be sure to include stdlib.h
	// if C.free is needed).
	func C.CBytes([]byte) unsafe.Pointer

	// C string to Go string
	func C.GoString(*C.char) string

	// C data with explicit length to Go string
	func C.GoStringN(*C.char, C.int) string

	// C data with explicit length to Go []byte
	func C.GoBytes(unsafe.Pointer, C.int) []byte

 */
```
__这些辅助函数都是以克隆的方式运行__。当Go语言字符串和切片向C语言转换时，克隆的内存通过C语言的`malloc`函数分配，最终**需要手动调用`free`函数释放内存**。当C语言字符串或数组向Go语言转换时，克隆的内存由Go语言分配管理。
