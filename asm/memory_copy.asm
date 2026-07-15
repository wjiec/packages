.intel_syntax noprefix
.global _start

.text
_start:
    lea rsi, [_buf]
    add rsi, 15

    mov rdi, rsi
    add rdi, 16

    _loop:
    mov al, [rsi]
    mov [rdi], al
    dec rsi
    sub rdi, 2
    cmp rsi, rsp
    jnz _loop


.data
    _buf:
    .quad 0x0123456789abcdef
    .quad 0x0123456789abcdef
    .skip 16, 0xff

