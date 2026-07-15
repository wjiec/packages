.intel_syntax noprefix
.global _start

.text
_start:

    lea r15, [_callstack]

    lea rax, [_buf1]
    lea rbx, [_buf1_len]
    lea r8, [_print_return1]
    lea r9, [_print]
    jmp _call

    _print_return1:
    lea rax, [_buf2]
    lea rbx, [_buf2_len]
    lea r8, [_print_return2]
    lea r9, [_print]
    jmp _call

    _print_return2:
    mov rax, 60
    mov rdi, 0
    syscall


    _print:
    mov rsi, rax
    mov rdx, rbx
    mov rax, 0x01
    mov rdi, 0x01
    syscall
    jmp _return

    _call:
    mov [r15], r8
    add r15, 8
    jmp r9

    _return:
    sub r15, 8
    jmp [r15]


.data
    _buf1: .ascii "Hello, "
    _buf1_len = . - _buf1

    _buf2: .ascii "world!\n"
    _buf2_len = . - _buf2

    _callstack: .skip 4096, 0xff
