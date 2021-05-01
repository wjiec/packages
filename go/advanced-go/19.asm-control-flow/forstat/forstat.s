
#include "textflag.h"

// func Add(cnt int, v0, step int) int
TEXT ·Add(SB), NOSPLIT, $8
    MOVQ $0, AX // result
    MOVQ cnt+0(FP), CX // cnt
    MOVQ v0+8(FP), BX // v0
    MOVQ step+16(FP), DX // step

    MOVQ $0, DI // i

    LOOP_IF:
    CMPQ DI, CX // compre i cnt
    JL LOOP_BODY // i < cnt -> loop
    JMP LOOP_END // return else

    LOOP_BODY:
    ADDQ BX, AX // result += v0
    ADDQ DX, BX // v0 += step
    ADDQ $1, DI // i++
    JMP LOOP_IF

    LOOP_END:
    MOVQ AX, ret+24(FP)
    RET
