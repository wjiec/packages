.intel_syntax noprefix
.global _start

.text
_start:
  lea rax, [_buf]
  call _print
  jmp _exit


_print: ;# rax = in string
  push rax
  call _strlen
  mov rdx, rax  ;# length
  pop rsi       ;# buffer
  mov rax, 0x01 ;# syscall WRITE
  mov rdi, 0x01 ;# stdout
  syscall
  ret

_strlen: ;# rax = in string, out len
  mov rsi, rax
  xor rax, rax
  _strlen_loop:
    mov al, [rsi]
    cmp al, 0x00
    je _strlen_done
    inc rsi
    inc rax
  _strlen_done:
    ret

_exit:
  mov rax, 0x3c
  mov rdi, 0x00
  syscall


.data
_buf: .ascii "Hello, world!\n"

