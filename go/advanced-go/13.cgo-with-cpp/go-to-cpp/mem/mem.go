package mem

import "sync"

type Addr int64

var memory struct {
	mu   sync.RWMutex
	objs map[Addr]interface{}
	next Addr
}

func init() {
	memory.objs = make(map[Addr]interface{})
}

func Ref(obj interface{}) (addr Addr) {
	memory.mu.Lock()
	addr = memory.next
	memory.objs[addr] = obj
	memory.next += 1
	memory.mu.Unlock()

	return
}

func Unref(addr Addr) {
	memory.mu.Lock()
	delete(memory.objs, addr)
	memory.mu.Unlock()
}

func Get(addr Addr) (v interface{}) {
	memory.mu.RLock()
	v, _ = memory.objs[addr]
	memory.mu.RUnlock()

	return
}
