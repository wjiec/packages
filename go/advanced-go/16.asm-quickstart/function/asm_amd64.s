
TEXT ·main(SB), $16-0
    MOVQ ·Name+0(SB), AX; MOVQ AX, 0(SP)
    MOVQ ·Name+8(SB), AX; MOVQ AX, 8(SP)
    CALL ·println(SB)
    RET
