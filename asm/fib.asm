.intel_syntax noprefix
.global _start

.text
_start:

    mov rax, 0x00
    mov rbx, 0x01

    _fib:
    mov rcx, rbx
    add rbx, rax
    mov rax, rcx
    jmp _fib
