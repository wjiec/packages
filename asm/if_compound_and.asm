.intel_syntax noprefix
.global _start

.text
_start:

  ;# if (x > 1 && x < 5) do_something
  mov rax, 5
  cmp rax, 1
  jle _exit
  cmp rax, 5
  jge _exit
  call _do_something

  _exit:
    mov rax, 0x3c
    mov rdi, 0x00
    syscall

  _do_something:
    mov rax, 0x01
    mov rdi, 0x01
    lea rsi, [_buf]
    lea rdx, [_buf_len]
    syscall
    ret

.data
_buf: .ascii "do something!\n"
_buf_len = . - _buf
