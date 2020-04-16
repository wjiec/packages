package aes

import "io"

// Aes represents a aes-cipher
type Aes struct {
	key string
}

// Write write data to p
func (a *Aes) Write(p []byte) (n int, err error) {
	panic("implement me")
}

// Read read data into p
func (a *Aes) Read(p []byte) (n int, err error) {
	panic("implement me")
}

// WriteTo write tarball-archive bytes into writer
func (a *Aes) WriteTo(writer io.Writer) error {
	panic("implement me")
}

// New create a aes
func New(key string) (*Aes, error) {
	return &Aes{key: key}, nil
}
