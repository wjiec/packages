
GLOBL ·Name(SB), $16
DATA ·Name+0(SB)/8, $·Name+16(SB) // ptr
DATA ·Name+8(SB)/8, $20 // length
DATA ·Name+16(SB)/20, $"hello world form asm"

GLOBL ·Truncation(SB), $16
DATA ·Truncation+0(SB)/8, $·Name+16(SB)
DATA ·Truncation+8(SB)/8, $11
