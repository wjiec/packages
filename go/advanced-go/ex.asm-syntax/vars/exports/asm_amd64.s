
// var ArrayValue [4]uint32
GLOBL ·ArrayValue(SB), $32
DATA ·ArrayValue+0(SB)/4, $0 // ArrayValue[0] = 0
DATA ·ArrayValue+4(SB)/4, $1 // ArrayValue[1] = 1
DATA ·ArrayValue+8(SB)/4, $2 // ArrayValue[2] = 2
DATA ·ArrayValue+12(SB)/4, $3 // ArrayValue[3] = 3

// var BoolValue bool
GLOBL ·BoolValue(SB), $1 // uninitial

// var TrueValue bool
GLOBL ·TrueValue(SB), $1
DATA ·TrueValue(SB)/1, $-1

// var FalseValue bool
GLOBL ·FalseValue(SB), $1
DATA ·FalseValue(SB)/1, $0

// var IntValue int
GLOBL ·IntValue(SB), $8
DATA ·IntValue(SB)/8, $0xffffffffffffffff

// var Float32Value float32
GLOBL ·Float32Value(SB), $4
DATA ·Float32Value(SB)/4, $0x12345678

// var Float64Value float32
GLOBL ·Float64Value(SB), $8
DATA ·Float64Value(SB)/8, $0x12345678

// var StringValue string
GLOBL ·StringValue(SB), $16
DATA ·StringValue+0(SB)/8, $·text<>(SB)
DATA ·StringValue+8(SB)/8, $5

// var SliceValue string
GLOBL ·SliceValue(SB), $24
DATA ·SliceValue+0(SB)/8, $·text<>(SB)
DATA ·SliceValue+8(SB)/8, $8
DATA ·SliceValue+16(SB)/8, $16

#include "textflag.h"

// internal
//GLOBL ·text<>(SB), 16, $16 // NOPTR
GLOBL ·text<>(SB), NOPTR, $16
//DATA ·text<>(SB)/8, $"hello world long long" // overflow // asm: WriteString: bad string size: 8 < 21
DATA ·text<>+0(SB)/8, $"hello"
