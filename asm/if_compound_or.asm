.intel_syntax noprefix
.global _start

.text
_start:

  mov rax, 6
  ;# if (x == 1 || x > 5) do_something
  cmp rax, 1
  je _body
  cmp rax, 5
  jg _body
  jmp _exit
  _body:
  call _do_something
  jmp _exit

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
