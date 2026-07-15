.intel_syntax noprefix
.global _start

.text
_start:
  ;# 1 = 0b0001
  ;# -1 = ~1 + 1
  ;#    = 0b1110 + 0b1
  ;#    = 0b1111
  mov ax, -1

  cmp ax, 1
  ;# SIGNED
  jg _exit  ;# ax > 1?
  jge _exit ;# ax >= 1?
  jl _exit  ;# ax < 1
  jle _exit ;# ax <= 1

  ;# UNSIGNED
  ja _exit  ;# jump if above (ax > 1)
  jae _exit ;# jump if above or equal to (ax >= 1)
  jb _exit  ;# jump if below (ax < 1)
  jbe _exit ;# jump if below or equal to (ax <= 1)

  je _exit  ;# jump if equal
  jne _exit ;# jump if not equal
  jz _exit  ;# jump if zero
  jnz _exit ;# jump if not zero

  _exit:
  mov rax, 0x3c
  mov rdi, 0x00
  syscall

