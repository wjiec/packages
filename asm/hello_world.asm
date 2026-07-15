.intel_syntax noprefix
.global _start

.text
    _start:

    ;# syscall code: rax
    ;# syscall args: rdi rsi rdx rcx r8 r9

    ;# SYSCALL_DEFINE3(write, unsigned int, fd, const char __user *, buf, size_t, count)
    mov rax, 0x01 ;# syscall WRITE
    mov rdi, 0x01 ;# stdout
    lea rsi, [_buf]
    lea rdx, [_buf_len]
    syscall

    ;# SYSCALL_DEFINE1(exit, int, error_code)
    mov rax, 0x3c ;# syscall EXIT
    mov rdi, 0x00 ;# error code
    syscall

.data
    _buf: .ascii "Hello, world!\n"
    _buf_len = . - _buf
