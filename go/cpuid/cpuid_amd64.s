#include "textflag.h"


// TODO golang unaligned stack size 12 ?
// func get(uint32) (uint32, uint32, uint32, uint32)
TEXT ·get(SB), NOSPLIT, $8-24
    MOVL arg+0(FP), AX
    CPUID

    MOVL AX, r1+8(FP)
    MOVL BX, r2+12(FP)
    MOVL CX, r3+16(FP)
    MOVL DX, r4+20(FP)
    RET
