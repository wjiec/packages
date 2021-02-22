package utils

import (
	"os"
	"strings"
)

func NormalizePath(path string) string {
	return strings.ReplaceAll(path, "\\", "/")
}

func IsFile(path string) bool {
	stat, err := os.Stat(path)
	if err != nil {
		return false
	}
	return !stat.IsDir()
}
