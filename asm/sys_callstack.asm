.intel_syntax noprefix
.global _start

.text
_start:
    call _print_hello
    call _exit

    _print_hello:
    lea rax, [_buf1]
    lea rbx, [_buf1_len]
    call _print
    call _print_world
    ret

    _print_world:
    lea rax, [_buf2]
    lea rbx, [_buf2_len]
    call _print
    ret


    _exit:
    mov rax, 60
    mov rdi, 0
    syscall

    _print:
    mov rsi, rax
    mov rdx, rbx
    mov rax, 0x01
    mov rdi, 0x01
    syscall
    ret

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
