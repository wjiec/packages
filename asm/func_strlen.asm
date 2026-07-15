.intel_syntax noprefix
.global _start

.text
  lea rax, [_buf]
  call _print
  jmp _exit


_strlen: ;# rax = in addr, out len
  mov r8, rax
  xor rax, rax
  _strlen_loop:
  mov dl, [r8]
  cmp dl, 0x00
  je _strlen_done
  inc rax
  inc r8
  jmp _strlen_loop
  _strlen_done:
  ret

_print: ;# rax = in addr
  mov rsi, rax  ;# buffer
  call _strlen
  mov rdx, rax  ;# len
  mov rax, 0x01 ;# syscall WRITE
  mov rdi, 0x01 ;# stdout
  syscall
  ret

_exit:
  mov rax, 0x3c
  mov rdi, 0x00
  syscall


.data
_buf: .asciz "Hello, world!\n"

