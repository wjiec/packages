.intel_syntax noprefix
.global _start

.text
_start:
  ;# 8b: al * ? => ah
  xor rax, rax
  mov cl, 100
  mov al, 230
  mul cl ;# 100 * 230 = 23000
  call _print_number
  ;# 16b: ax * ? = dx:ax
  xor rax, rax
  xor rdx, rdx
  mov ax, 0xaaaa
  mov cx, 0x1111
  mul cx ;# 0xaaaa * 0x1111 = 0xb609f4a = 190881610
  shl rdx, 16
  or rax, rdx
  call _print_number
  ;# 32b: eax * ? = edx:eax
  xor rax, rax
  xor rdx, rdx
  mov eax, 0x12345678
  mov ecx, 0x87654321
  mul ecx ;# 0x12345678 * 0x87654321 = 0x9a0cd0570b88d78 = 693779765864729976
  shl rdx, 32
  or rax, rdx
  call _print_number
  ;# 64b: rax * ? = rdx:rax
  xor rax, rax
  xor rdx, rdx
  mov rax, 0x1234567890abcdef
  mov rcx, 0x8765432101234567
  mul rcx
  call _print_number
  mov rax, rdx
  call _print_number
  ;# 8b: ax / ? = ah .. al
  xor rax, rax
  mov ax, 0xab
  mov rcx, 0x02
  div cl
  mov cl, ah
  and ax, 0xff
  call _print_number
  mov al, cl
  call _print_number
  ;# 16b: dx:ax / ? = ax .. dx
  xor rax, rax
  xor rdx, rdx
  mov dx, 0x1234
  mov ax, 0x5678
  mov cx, 0x1a2b
  div cx
  call _print_number
  mov rax, rdx
  call _print_number
  ;# 32b: edx:eax / ? = eax .. edx
  xor rax, rax
  xor rdx, rdx
  mov edx, 0x12345678
  mov eax, 0x5678abcd
  mov ecx, 0x1a2b3c4d
  div ecx
  call _print_number
  mov rax, rdx
  call _print_number
  ;# 64b: rdx:rax / ? = rax .. rdx
  xor rax, rax
  xor rdx, rdx
  mov rdx, 0x12345678abcd
  mov rax, 0x5678abcdef01
  mov rcx, 0x1a2b3c4d5e6f
  div rcx
  call _print_number
  mov rax, rdx
  call _print_number
  jmp _exit

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
    ;# cqo ;# extends eax to rdx:rax
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
_buf: .skip 512, 0

