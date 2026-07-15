.intel_syntax noprefix
.global _start

;# x86-64 System V ABI (https://wiki.osdev.org/System_V_ABI)
;# Functions preserve the registers rbx, rsp, rbp, r12, r13, r14, and r15;
;# while rax, rdi, rsi, rdx, rcx, r8, r9, r10, r11 are scratch registers.

.text
_start:
  call _fn_main

  lea rdi, [shell_exited]
  call _fn_println
  jmp _fn_exit

_fn_main:
  _fn_main.body:
    _fn_main.body.loop:
      lea rdi, [shell_prompt]
      call _fn_print

      lea rdi, [readbuf]
      lea rsi, [readbuf_len]
      call _fn_readline

      ;# check is 'exit'
      lea rdi, [readbuf]
      lea rsi, [shell_exit_cmd]
      call _fn_strcmp
      cmp rax, 0x00
      je _fn_main.done

      ;# parse commands
      lea rdi, [readbuf]
      call _fn_exec_cmd

      jmp _fn_main.body.loop
  _fn_main.done:
    ret

_fn_exec_cmd: ;# rdi = command with args
  _fn_exec_cmd.body:
    lea rsi, [exec_args_buf]
    lea rdx, [exec_args]
    call _fn_parse_args

    ;# no command
    cmp rax, 0
    je _fn_exec_cmd.done

    ;# check first command
    mov rdi, [exec_args]
    lea rsi, [shell_echo_cmd]
    call _fn_strcmp
    cmp rax, 0x00 ;# cmd == "echo"
    je _fn_exec_cmd.echo

    ;# command not found
    lea rdi, [shell_command_notfound]
    call _fn_print
    mov rdi, [exec_args]
    call _fn_println

    jmp _fn_exec_cmd.done
  _fn_exec_cmd.echo:
    lea rdi, [exec_args]
    call _fn_cmd_echo
    jmp _fn_exec_cmd.done
  _fn_exec_cmd.done:
    ret

_fn_parse_args: ;# rdi = buffer, rsi = target buffer, rdx = string array, out:rax = parsed array size
  sub rsp, 40
  mov qword ptr [rsp], rdi
  mov qword ptr [rsp + 8], rsi
  mov qword ptr [rsp + 16], rdx
  mov qword ptr [rsp + 24], 0x00
  _fn_parse_args.body:
    _fn_parse_args.body.loop:
      mov rdi, [rsp]
      call _fn_skip_spaces
      cmp rax, -1
      je _fn_parse_args.done
      cmp rax, 0x00
      jne _fn_parse_args.body.loop.body
      _fn_parse_args.body.loop.body:
        add [rsp], rax ;# moves to next arg

        mov rdi, [rsp]
        mov rsi, ' '
        call _fn_strchr
        mov [rsp + 32], rax ;# current end

        mov rdi, [rsp] ;# src
        mov rsi, [rsp + 8] ;# dst
        mov rdx, [rsp + 32] ;# length
        call _fn_strcpy

        ;# update array
        mov rsi, [rsp + 8]
        mov rdx, [rsp + 16]
        mov [rdx], rsi ;# args[..] = rsi
        add qword ptr [rsp + 16], 0x08

        ;# update dst
        mov rax, [rsp + 32]
        add rsi, rax
        mov byte ptr [rsi], 0x00
        inc rsi ;# moved to next pos
        mov [rsp + 8], rsi

        ;# count++
        add qword ptr [rsp + 24], 0x01

        ;# update src
        mov rdi, [rsp]
        mov rax, [rsp + 32]
        cmp byte ptr [rdi + rax], 0x00 ;# check if eof
        je _fn_parse_args.done
        add [rsp], rax ;# rdi += len

        jmp _fn_parse_args.body.loop
  _fn_parse_args.done:
    mov rdi, [rsp + 16]
    mov qword ptr [rdi], 0x00
    mov rax, [rsp + 24]
    add rsp, 40
    ret

_fn_cmd_echo: ;# rdi = args
  sub rsp, 0x08
  mov [rsp], rdi
  add qword ptr [rsp], 0x08 ;# skip args[0]
  _fn_cmd_echo.body:
    _fn_cmd_echo.body.loop:
      mov rsi, [rsp]
      cmp qword ptr [rsi], 0x00
      je _fn_cmd_echo.done

      lea rdi, [shell_echo_prefix]
      call _fn_print

      mov rsi, [rsp]
      mov rdi, [rsi]
      call _fn_println
      add qword ptr [rsp], 0x08
      jmp _fn_cmd_echo.body.loop
  _fn_cmd_echo.done:
    add rsp, 0x08
    ret

_fn_skip_spaces: ;# rdi = buffer, out:rax = skipped length
  xor rax, rax
  _fn_skip_spaces.body:
    _fn_skip_spaces.body.loop:
    cmp byte ptr [rdi + rax], 0x00
    je _fn_skip_spaces.end
    cmp byte ptr [rdi + rax], 0x20
    jne _fn_skip_spaces.done
    inc rax
    jmp _fn_skip_spaces.body.loop
  _fn_skip_spaces.end:
    mov rax, -1
  _fn_skip_spaces.done:
    ret

_fn_readline: ;# rdi = buffer, rsi = buffer length
  mov r8, rdi
  _fn_readline.body:
    ;# SYSCALL_DEFINE3(read, unsigned int, fd, char __user *, buf, size_t, count)
    mov rdx, rsi ;# count
    mov rsi, rdi ;# buffer
    mov rax, 0x00 ;# syscall READ
    mov rdi, 0x00 ;# stdin
    syscall
  _fn_readline.compatible:
    cmp byte ptr [r8 + rax - 1], '\n'
    jne _fn_readline.done
    dec rax
  _fn_readline.done:
    mov byte ptr [r8 + rax], 0x00
    ret

_fn_print: ;# rdi = buffer
  call _fn_strlen
  _fn_print.body:
    mov rdx, rax ;# length
    mov rsi, rdi ;# buffer
    mov rax, 0x01 ;# syscall WRITE
    mov rdi, 0x01 ;# stdout
    syscall
  _fn_print.done:
    ret

_fn_println: ;# rdi = buffer
  cmp rdi, 0x00
  je _fn_println.body
  call _fn_print
  _fn_println.body:
    mov rax, 0x01 ;# syscall WRITE
    mov rdi, 0x01 ;# stdout
    lea rsi, [newline] ;# buffer
    mov rdx, 0x01 ;# length
    syscall
  _fn_println.done:
    ret

_fn_strlen: ;# rdi = buffer, out:rax = length of string without \0
  xor rax, rax
  _fn_strlen.body:
    cmp byte ptr [rdi + rax], 0x00
    je _fn_strlen.done
    inc rax
    jmp _fn_strlen.body
  _fn_strlen.done:
    ret

_fn_strchr: ;# rdi = buffer, rsi = char, out:rax = the first occurrence index
  mov rcx, rsi
  xor rax, rax
  _fn_strchr.body:
    _fn_strchr.body.loop:
      cmp byte ptr [rdi + rax], 0x00
      je _fn_strchr.done
      cmp byte ptr [rdi + rax], cl
      je _fn_strchr.done
      inc rax
      jmp _fn_strchr.body.loop
  _fn_strchr.done:
    ret

_fn_strcmp: ;# rdi = buffer1, rsi = buffer2 out:rax = result
  xor rcx, rcx
  xor rdx, rdx
  _fn_strcmp.body:
    _fn_strcmp.body.loop:
      mov cl, [rdi]
      mov dl, [rsi]
      cmp cl, dl
      jl _fn_strcmp.body.lt ;# rdi < rsi
      jg _fn_strcmp.body.gt ;# rdi > rsi
      cmp cl, 0 ;# rdi == rsi && rdi == 0x00
      je _fn_strcmp.body.eq

      inc rdi
      inc rsi
      jmp _fn_strcmp.body.loop
  _fn_strcmp.body.lt:
    mov rax, -1
    jmp _fn_strcmp.done
  _fn_strcmp.body.gt:
    mov rax, 1
    jmp _fn_strcmp.done
  _fn_strcmp.body.eq:
    mov rax, 0
    jmp _fn_strcmp.done
  _fn_strcmp.done:
    ret

_fn_strcpy: ;# rdi = src buffer, rsi = dst buffer, rdx = count
  xor rcx, rcx
  _fn_strcpy.body:
    _fn_strcpy.loop:
      mov cl, [rdi]
      mov byte ptr [rsi], cl
      inc rdi
      inc rsi
      dec rdx
      cmp rdx, 0x00
      je _fn_strcpy.done
      jmp _fn_strcpy.loop
  _fn_strcpy.done:
    ret

_fn_exit:
  mov rax, 0x3c ;# syscall EXIT
  mov rdi, 0x00 ;# exit code
  syscall

_fn_memview:
  mov rsi, rsp
  mov rsp, rdi
  int3
  mov rsp, rsi

.data
;# kernel buffer
readbuf: .skip 4096, 0xff
readbuf_len = . - readbuf

;# shell exec
exec_args: .skip 512, 0xff ;# array
exec_args_buf: .skip 4096, 0xff ;# args buffer
exec_args_buf_len = . - exec_args_buf

;# bss
newline: .byte '\n'
shell_prompt: .string "$ "
shell_exit_cmd: .string "exit"
shell_echo_cmd: .string "echo"
shell_exited: .string "[exited]"
shell_command_notfound: .string "[command not found]: "
shell_echo_prefix: .string "echo: "
