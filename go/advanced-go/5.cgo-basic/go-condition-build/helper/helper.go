// +build !windows,!linux

package helper

func Platform() string {
	return "unknown"
}
