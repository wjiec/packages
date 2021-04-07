package main

import (
	"fmt"
	"sync"
	"sync/atomic"
	"unsafe"
)

type Singleton struct{}

var ok uint32
var lock sync.Mutex
var singleton *Singleton

func GetInstance() *Singleton { // alias for sync.Once
	// 首先执行轻量级的原子检测，如果有值，表示已经初始化，直接返回
	if atomic.LoadUint32(&ok) == 1 {
		return singleton
	}

	// 轻量级的原子检测失败之后，进行重量级的加锁操作
	lock.Lock() // 保证只有一个线程可以进入

	// 加锁完成之后需要再次检查是否已经完成初始化，因为可能会有很多线程阻塞在锁上
	if atomic.LoadUint32(&ok) == 0 {
		// 如果还未初始化，则执行初始化
		singleton = &Singleton{}
		// 并且执行赋值操作
		atomic.StoreUint32(&ok, 1)
	}

	// 完成之后解锁并返回
	lock.Unlock()
	return singleton
}

func main() {
	start := sync.WaitGroup{}
	start.Add(1)

	wg := sync.WaitGroup{}
	for i := 0; i < 1000; i++ {
		wg.Add(1)

		go func() {
			start.Wait()
			fmt.Println(unsafe.Pointer(GetInstance()))
			wg.Done()
		}()
	}

	start.Done()
	wg.Wait()
}
