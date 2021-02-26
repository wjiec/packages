package protobuf

import (
	"errors"
	"os/exec"
	"path/filepath"
	"protob/pkg/os/fs"
	"strings"
)

var (
	// ErrCompilerNotFound represents unable to found protobuf compiler on system path
	ErrCompilerNotFound = errors.New("protoc: not found")
	// ErrCompilerInvalid represents protobuf compiler not a executable or something error
	ErrCompilerInvalid = errors.New("protoc: invalid executable")
)

// Compiler represents a protobuf compiler
type Compiler struct {
	// Compiler version number
	Version string

	// path of the compiler
	path string
}

// Compile compile protobuf into go file
func (c *Compiler) Compile(target string, dependencies []string) error {
	args := []string{target}
	for _, dependency := range dependencies {
		args = append(args, "--proto_path", dependency)
	}
	args = append(args, "--proto_path", filepath.Dir(target))

	_, err := exec.Command(c.path, args...).Output()
	return err
}

// NewCompiler create a compiler from path
func NewCompiler(path string) (*Compiler, error) {
	if path != "" {
		if ok, _ := fs.IsFile(path); ok {
			if output, err := exec.Command(path, "--version").Output(); err == nil {
				return &Compiler{path: path, Version: strings.TrimSpace(string(output))}, nil
			}
		}
	}

	return nil, ErrCompilerInvalid
}

// NewSystemCompiler create a compiler lookup form system path
func NewSystemCompiler() (*Compiler, error) {
	if path, err := exec.LookPath(CompilerExecutable); err != nil {
		return nil, ErrCompilerNotFound
	} else {
		return NewCompiler(path)
	}
}
