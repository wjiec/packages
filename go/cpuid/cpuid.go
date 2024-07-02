package main

// see https://www.ichenfu.com/2020/12/17/cpuinfo-model-name/
//

func get(_ uint32) (uint32, uint32, uint32, uint32)

func Get(code uint32) (uint32, uint32, uint32, uint32) {
	return get(code)
}
