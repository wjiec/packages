.intel_syntax noprefix
.global _start

.text
_start:

  ;# for (int i = $rbx; i < $rcx; i++) { ... }
  mov rbx, 0
  mov rcx, 10
  _for:
  cmp rbx, rcx
  jge _exit
  ;# for loop body
  call _print
  ;# end for
  inc rbx
  jmp _for

  _exit:
    mov rax, 0x3c
    mov rdi, 0x00
    syscall

  _print:
    mov rax, 0x01
    mov rdi, 0x01
    lea rsi, [_buf]
    lea rdx, [_buf_len]
    syscall
    ret

.data
_buf: .ascii "Hello, world!\n"
_buf_len = . - _buf
