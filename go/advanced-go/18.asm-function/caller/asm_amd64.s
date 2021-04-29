
#include "textflag.h"


// func sub(a, b int) int
TEXT ·sub(SB), NOSPLIT, $0
    MOVQ a+0(FP), AX
    SUBQ b+8(FP), AX
    MOVQ AX, r+16(FP) // [1]
    RET

// func printsub(x, y int)
TEXT ·printsub(SB), $16 // sub(a, b) = 16, println(a) = 8 /// = 16
    MOVQ x+0(FP), AX // x -> AX
    MOVQ AX, +0(SP) // AX -> sub.a
    MOVQ y+8(FP), AX // y -> AX
    MOVQ AX, +8(SP) // AX -> sub.b
    CALL ·sub(SB) // sub.ret -> +16(SP) [1]
    MOVQ +16(SP), AX // +16(SP) -> AX
    MOVQ AX, +0(SP) // AX -> printsub.ret
    CALL ·println(SB) // println(+0(SP))
    RET


