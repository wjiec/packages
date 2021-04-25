package fs

import "os"

func Dir(filename string) (bool, error) {
	stat, err := os.Stat(filename)
	if err != nil {
		return false, err
	}

	return stat.IsDir(), nil
}
