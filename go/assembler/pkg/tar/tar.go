package tar

import (
	"archive/tar"
	"bytes"
	"io"
	"sync"
)

// Archive represents a tarball-archive
type Archive struct {
	name   string
	mu     *sync.Mutex
	buffer *bytes.Buffer
	writer *tar.Writer
}

// Add add file or folder into tarball-archive
func (a *Archive) Add(abs string, rel string) error {
	return nil
}

// AddFile add file into tarball-archive
func (a *Archive) AddFile(abs string, rel string) error {
	return nil
}

// AddFolder add folder into tarball-archive
func (a *Archive) AddFolder(abs string, rel string) error {
	return nil
}

// WriteTo write tarball-archive bytes into writer
func (a *Archive) WriteTo(writer io.Writer) error {
	return nil
}

// Read read data into p
func (a *Archive) Read(p []byte) (n int, err error) {
	panic("implement me")
}

// New create a tarball archive
func New(name string) *Archive {
	buffer := new(bytes.Buffer)

	return &Archive{
		name:   name,
		mu:     new(sync.Mutex),
		buffer: buffer,
		writer: tar.NewWriter(buffer),
	}
}
