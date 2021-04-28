
#include "textflag.h"

// func Swap(a, b int) (r0, r1 int)
TEXT ·Swap(SB), NOSPLIT, $0 // no local vars
    MOVQ a+0(FP), AX
    MOVQ b+8(FP), BX
    MOVQ BX, r0+16(FP)
    MOVQ AX, r1+24(FP)
    RET
