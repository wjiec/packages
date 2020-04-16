package gzip

import "io"

// Gzip represents a gzip-compress
type Gzip struct {
}

// Write write data to p
func (g *Gzip) Write(p []byte) (n int, err error) {
	panic("implement me")
}

// Read read data into p
func (g *Gzip) Read(p []byte) (n int, err error) {
	panic("implement me")
}

// WriteTo write tarball-archive bytes into writer
func (g *Gzip) WriteTo(writer io.Writer) error {
	return nil
}

// New create a gzip-compression
func New() *Gzip {
	return &Gzip{}
}
