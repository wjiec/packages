package main

import (
	"fmt"
	"os"
	"slices"
	"strconv"
	"strings"
	"unicode"
)

// references:
//	- http://www.flounder.com/cpuid_explorer1.htm
//	- http://www.flounder.com/cpuid_explorer2.htm
//	- https://en.wikipedia.org/wiki/CPUID

var cheatsheet = map[uint32]string{
	0:          "Basic information",
	1:          "Extended basic information",
	2:          "Cache and TLB information",
	3:          "Processor Serial Number",
	4:          "Deterministic Cache Parameters",
	5:          "Monitor/MWAIT information",
	6:          "Thermal/Power Management",
	0xa:        "Architectural Performance Monitoring",
	0x80000000: "Extended information",
	0x80000001: "Extended processor signature and feature bits (Intel)",
	0x80000002: "Processor brand string",
	0x80000003: "Processor brand string",
	0x80000004: "Processor brand string",
	0x80000005: "Reserved (Intel)",
	0x80000006: "L2 cache information (Intel)",
	0x80000008: "Physical and virtual memory limits",
	0x8000000a: "Secure Virtual Machine specifications",
}

func main() {
	var features = os.Args[1:]
	if len(features) == 0 {
		var rawFeatures = make([]uint32, 0, len(cheatsheet))
		for feature := range cheatsheet {
			rawFeatures = append(rawFeatures, feature)
		}

		slices.Sort(rawFeatures)
		for _, feature := range rawFeatures {
			features = append(features, strconv.Itoa(int(feature)))
		}
	}

	for _, arg := range features {
		code, err := parseInt(arg)
		if err != nil {
			fmt.Printf("CPUID(%#x): %s\n\n\n", features, err.Error())
			continue
		}

		r1, r2, r3, r4 := Get(uint32(code))
		fmt.Printf("CPUID(%#x): %s\n\tEAX: %s\n\tEBX: %s\n\tECX: %s\n\tEDX: %s\n\n\n", code,
			cheatsheet[uint32(code)],
			humanize(r1), humanize(r2), humanize(r3), humanize(r4))
	}
}

func humanize(value uint32) string {
	a, b, c, d := (value>>24)&0xff, (value>>16)&0xff, (value>>8)&0xff, value&0xff
	return fmt.Sprintf("%02x %02x %02x %02x\t%08b %08b %08b %08b\t%c%c%c%c",
		a, b, c, d, a, b, c, d, printable(a), printable(b), printable(c), printable(d))
}

func printable(v uint32) rune {
	if unicode.IsPrint(rune(v)) {
		return rune(v)
	}
	return '.'
}

func parseInt(s string) (int64, error) {
	s = strings.ToLower(s)
	if strings.HasPrefix(s, "0x") {
		return strconv.ParseInt(s[2:], 16, 64)
	}

	if strings.HasPrefix(s, "0") && len(s) > 1 {
		return strconv.ParseInt(s, 8, 64)
	}

	return strconv.ParseInt(s, 10, 64)
}
