
#include "textflag.h"

// func If(ok bool, a, b int) int
TEXT ·If(SB), NOSPLIT, $0
    MOVBQZX ok+0(FP), AX // ax = ok
    MOVQ a+8(FP), BX // bx = a
    MOVQ b+16(FP), CX // cx = b

    CMPQ AX, $0
    JZ OTHER
    MOVQ BX, ret+24(FP)
    RET

    OTHER:
    MOVQ CX, ret+24(FP)
    RET
