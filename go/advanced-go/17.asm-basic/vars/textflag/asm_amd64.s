
#include "textflag.h"

GLOBL ·PointValue(SB), $8
GLOBL ·ArrayValue(SB), $8

DATA ·PointValue(SB)/8, $·ArrayValue(SB)
DATA ·ArrayValue+0(SB)/4, $123
DATA ·ArrayValue+4(SB)/4, $456

GLOBL ·IntValue(SB), RODATA, $8
DATA ·IntValue(SB)/8, $123456
