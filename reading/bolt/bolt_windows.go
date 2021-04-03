package bolt

import (
	"fmt"
	"os"
	"syscall"
	"time"
	"unsafe"
)

// LockFileEx code derived from golang build filemutex_windows.go @ v1.5.1
var (
	modkernel32      = syscall.NewLazyDLL("kernel32.dll")
	procLockFileEx   = modkernel32.NewProc("LockFileEx")
	procUnlockFileEx = modkernel32.NewProc("UnlockFileEx")
)

const (
	lockExt = ".lock"

	// see https://msdn.microsoft.com/en-us/library/windows/desktop/aa365203(v=vs.85).aspx
	flagLockExclusive       = 2
	flagLockFailImmediately = 1 // 快速失败模式，不会堵塞？

	// see https://msdn.microsoft.com/en-us/library/windows/desktop/ms681382(v=vs.85).aspx
	// The process cannot access the file because another process has locked a portion of the file.
	errLockViolation syscall.Errno = 0x21
)

// 调用windows API执行上锁操作
func lockFileEx(h syscall.Handle, flags, reserved, locklow, lockhigh uint32, ol *syscall.Overlapped) (err error) {
	r, _, err := procLockFileEx.Call(uintptr(h), uintptr(flags), uintptr(reserved), uintptr(locklow), uintptr(lockhigh), uintptr(unsafe.Pointer(ol)))
	if r == 0 {
		return err
	}
	return nil
}

func unlockFileEx(h syscall.Handle, reserved, locklow, lockhigh uint32, ol *syscall.Overlapped) (err error) {
	r, _, err := procUnlockFileEx.Call(uintptr(h), uintptr(reserved), uintptr(locklow), uintptr(lockhigh), uintptr(unsafe.Pointer(ol)), 0)
	if r == 0 {
		return err
	}
	return nil
}

// fdatasync flushes written data to a file descriptor.
func fdatasync(db *DB) error {
	return db.file.Sync()
}

// flock 会通过创建锁文件进行逻辑上锁
// flock acquires an advisory lock on a file descriptor.
func flock(db *DB, mode os.FileMode, exclusive bool, timeout time.Duration) error {
	// 在windows上将创建一个锁文件，因为一个进程不能在一个文件上共享排它锁
	// Create a separate lock file on windows because a process
	// cannot share an exclusive lock on the same file. This is
	// needed during Tx.WriteTo().
	// 尝试创建一个锁文件，这将在文件已存在时报错
	f, err := os.OpenFile(db.path+lockExt, os.O_CREATE, mode)
	if err != nil {
		return err
	}
	// 保存锁文件句柄
	db.lockfile = f

	var t time.Time
	for {
		// 如果超出超时时间则报错，这只有在至少尝试一次之后才会发生。
		// If we're beyond our timeout then return an error.
		// This can only occur after we've attempted a flock once.
		if t.IsZero() {
			// 默认为当前时间
			t = time.Now()
		} else if timeout > 0 && time.Since(t) > timeout {
			// 如果过去的时间是否超出了超时时间，直接报错
			return ErrTimeout
		}

		// 设置上锁快速失败模式
		var flag uint32 = flagLockFailImmediately
		if exclusive {
			// 检查是否需要增加独有模式
			flag |= flagLockExclusive
		}

		// 对锁文件执行上锁操作
		err := lockFileEx(syscall.Handle(db.lockfile.Fd()), flag, 0, 1, 0, &syscall.Overlapped{})
		if err == nil {
			return nil
		} else if err != errLockViolation { // 当前进程无法访问该文件，因为该文件已被其他进程锁定
			// The process cannot access the file because another process has locked a portion of the file.
			return err
		}

		// 如果文件呗占用则休息50ms之后再次执行
		// Wait for a bit and try again.
		time.Sleep(50 * time.Millisecond)
	}
}

// funlock releases an advisory lock on a file descriptor.
func funlock(db *DB) error {
	err := unlockFileEx(syscall.Handle(db.lockfile.Fd()), 0, 1, 0, &syscall.Overlapped{})
	db.lockfile.Close()
	os.Remove(db.path + lockExt)
	return err
}

// 创建数据库数据文件的内存映射
// mmap memory maps a DB's data file.
// Based on: https://github.com/edsrzf/mmap-go
func mmap(db *DB, sz int) error {
	// 如果数据库不是只读的，则需要截断到内存映射长度
	if !db.readOnly {
		// Truncate the database to the size of the mmap.
		if err := db.file.Truncate(int64(sz)); err != nil {
			return fmt.Errorf("truncate: %s", err)
		}
	}

	// 创建一个文件映射句柄
	// Open a file mapping handle.
	sizelo := uint32(sz >> 32)
	sizehi := uint32(sz) & 0xffffffff
	h, errno := syscall.CreateFileMapping(syscall.Handle(db.file.Fd()), nil, syscall.PAGE_READONLY, sizelo, sizehi, nil)
	if h == 0 {
		return os.NewSyscallError("CreateFileMapping", errno)
	}

	// 创建内存映射
	// Create the memory map.
	addr, errno := syscall.MapViewOfFile(h, syscall.FILE_MAP_READ, 0, 0, uintptr(sz))
	if addr == 0 {
		return os.NewSyscallError("MapViewOfFile", errno)
	}

	// 关闭文件映射句柄
	// Close mapping handle.
	if err := syscall.CloseHandle(syscall.Handle(h)); err != nil {
		return os.NewSyscallError("CloseHandle", err)
	}

	// 转换为直接数组引用
	// Convert to a byte array.
	db.data = ((*[maxMapSize]byte)(unsafe.Pointer(addr)))
	db.datasz = sz

	return nil
}

// 执行关闭内存映射
// munmap unmaps a pointer from a file.
// Based on: https://github.com/edsrzf/mmap-go
func munmap(db *DB) error {
	if db.data == nil {
		return nil
	}

	// 获得内存映射起始地址
	addr := (uintptr)(unsafe.Pointer(&db.data[0]))
	// 关闭内存映射，如果失败则返回错误
	if err := syscall.UnmapViewOfFile(addr); err != nil {
		return os.NewSyscallError("UnmapViewOfFile", err)
	}
	return nil
}
