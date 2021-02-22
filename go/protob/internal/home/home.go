package home

import (
	"errors"
	"os/exec"
	"path/filepath"
	"protob/pkg/utils"
	"runtime"
	"strings"

	"github.com/mitchellh/go-homedir"
)

var (
	// ErrCompilerNotFound represents unable to found protobuf compiler on system path
	ErrCompilerNotFound = errors.New("protoc: not found")
	// ErrCompilerInvalid represents protobuf compiler not a executable or something error
	ErrCompilerInvalid = errors.New("protoc: invalid")
)

// State represents a home of the compiler and dependencies info
type State struct {
	// Path of the Protobuf compiler(system)
	SysCompiler string

	// Path of the Protobuf compiler(embedded)
	EmbeddedCompiler string

	// Path of the Google dependencies
	GoogleDependency string
}

// SysCompilerVersion returns the version string of the protobuf compiler(system)
func (s *State) SysCompilerVersion() (string, error) {
	return compilerVersion(s.SysCompiler)
}

// EmbeddedCompilerVersion returns the version string of the protobuf compiler(embedded)
func (s *State) EmbeddedCompilerVersion() (string, error) {
	return compilerVersion(s.EmbeddedCompiler)
}

// BaseDir returns path of the protob home
func BaseDir() string {
	home, _ := homedir.Dir()

	return utils.NormalizePath(filepath.Join(home, ".protob"))
}

// ExpandDir returns joined path based on protob home
func ExpandDir(els ...string) string {
	return utils.NormalizePath(filepath.Join(append([]string{BaseDir()}, els...)...))
}

// ExpandExecutable returns executable filename base on protob home
func ExpandExecutable(els ...string) string {
	filename := ExpandDir(els...)
	if runtime.GOOS == "windows" {
		return filename + ".exe"
	}
	return filename
}

// compilerVersion returns the executable version string
func compilerVersion(executable string) (string, error) {
	if executable != "" && utils.IsFile(executable) {
		cmd := exec.Command(executable, "--version")
		output, err := cmd.Output()
		if err != nil {
			return "", ErrCompilerInvalid
		}

		return strings.TrimSpace(string(output)), nil
	}

	return "", ErrCompilerNotFound
}
