package benchmarkjoin

func Join(a []string, sep string) string {
	if len(a) == 0 {
		return ""
	} else if len(a) == 1 {
		return a[0]
	}

	s := a[0]
	for i := 1; i < len(a); i++ {
		s += sep + a[i]
	}
	return s
}
