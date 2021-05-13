
#include "textflag.h"


// func GetId() *int64
TEXT ·GetId(SB), NOSPLIT, $0
    MOVQ (TLS), AX
    ADDQ $152, AX // goid offset
    MOVQ AX, ret+0(FP)
    RET
