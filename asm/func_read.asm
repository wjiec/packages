.intel_syntax noprefix
.global _start

.text
_start:
  lea rax, [_read_buf]
  call _read
  call _print_number

  lea rax, [_read_buf]
  call _print
  jmp _exit

_read: ;# rax = in buffer
  push rdi
  push rsi
  push rdx
  _read_body:
    mov rsi, rax ;# buffer
    mov rax, 0x00 ;# syscall READ
    mov rdi, 0x00 ;# stdin
    mov rdx, 4096 ;# buffer length
    syscall
  _read_done:
    pop rdx
    pop rsi
    pop rdi
    ret

_print_number: ;# rax = in number
  push rax
  push rbx
  lea rbx, [_buf]
  call _itoa
  lea rax, [_buf]
  call _print
  ;# print newline
  mov rax, 0x000a ;# \0\n
  call _print_literal
  pop rbx
  pop rax
  ret

_print_literal: ;# rax = in string
  push rax
  push rbx
  lea rbx, [_buf]
  mov [rbx], rax
  lea rax, [_buf]
  call _print
  pop rbx
  pop rax
  ret

_itoa: ;# rax = in number, rbx = in buffer, rax = out len
  push rdi
  push rcx
  push rbx
  push rdx
  ;# rdx:rax / rbx = rax ... rdx
  mov rcx, 10 ;# divisor
  mov rdi, rbx ;# target buffer
  _itoa_loop:
    xor rdx, rdx
    cqo ;# extends eax to rdx:rax
    idiv rcx ;# rdx:rax / 10
    add rdx, 0x30 ;# '0' + reminder
    mov [rdi], rdx
    inc rdi
    cmp rax, 0
    jne _itoa_loop
  _itoa_done:
    ;# add terminal character
    mov rcx, 0x00
    mov [rdi], rcx
    ;# swap
    mov rcx, rdi
    sub rcx, rbx ;# len
    mov rax, rbx ;# left addr
    mov rbx, rdi ;# right addr
    dec rbx ;# r = n - 1
    call _reverse
    ;# return
    mov rax, rcx
    pop rdx
    pop rbx
    pop rcx
    pop rdi
    ret

_reverse: ;# rax = in left_addr, rbx = in right_addr
  push rax
  push rbx
  push rcx
  _reverse_loop:
    cmp rax, rbx
    jge _reverse_done
    ;# swap
    mov cl, [rax]
    mov ch, [rbx]
    mov [rax], ch
    mov [rbx], cl
    ;# inc && dec
    inc rax
    dec rbx
    jmp _reverse_loop
  _reverse_done:
    pop rcx
    pop rbx
    pop rax
    ret

_print: ;# rax = in string
  push rsi
  push rdi
  push rdx
  push rax ;# backup buffer
  call _strlen
  mov rdx, rax  ;# length
  pop rsi       ;# buffer
  mov rax, 0x01 ;# syscall WRITE
  mov rdi, 0x01 ;# stdout
  syscall
  ;# return
  pop rdx
  pop rdi
  pop rsi
  ret

_strlen: ;# rax = in string, rax = out len
  push rcx
  push rsi
  mov rsi, rax
  xor rax, rax
  _strlen_loop:
    mov cl, [rsi]
    cmp cl, 0x00
    je _strlen_done
    inc rsi
    inc rax
    jmp _strlen_loop
  _strlen_done:
    pop rsi
    pop rcx
    ret

_exit:
  mov rax, 0x3c
  mov rdi, 0x00
  syscall


.data
_read_buf: .skip 4096, 0
_buf: .skip 4096, 0

