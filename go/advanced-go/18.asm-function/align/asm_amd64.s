
#include "textflag.h"

// func Align(a bool, b uint16) (bs []byte)
TEXT ·Align(SB), NOSPLIT, $40
    MOVBQZX a+0(FP), AX
    MOVWQZX b+2(FP), BX
    MOVW AX, data0+32(FP) // bs[0:1] = a
    MOVW BX, data1+34(FP) // bs[2:3] = b
    LEAQ data0+32(FP), CX // cx = &(fp+32)
    MOVQ CX, r_data+8(FP) // slice.data
    MOVQ $8, r_len+16(FP) // slice.len
    MOVQ $8, r_cap+24(FP) // slice.cap
    RET
