package aes

import (
	"bytes"
	"crypto/aes"
	"crypto/cipher"
	"io"
)

// Aes represents a aes-cipher
type Aes struct {
	key    string
	writer io.Writer
	reader io.Reader
	stream cipher.Stream
}

// Read read data into p
func (a *Aes) Read(p []byte) (n int, err error) {
	data := make([]byte, len(p))
	n, err = a.reader.Read(data)
	if err == nil {
		a.stream.XORKeyStream(p, data)
	}
	return n, err
}

// Write write data to p
func (a *Aes) Write(p []byte) (n int, err error) {
	dst := make([]byte, len(p))
	a.stream.XORKeyStream(dst, p)

	return a.writer.Write(dst)
}

// NewEncrypt create an aes-encrypt stream
func NewEncrypt(key string, writer io.Writer) (*Aes, error) {
	cph, err := NewCipher(key)
	if err != nil {
		return nil, err
	}

	return &Aes{
		key:    key,
		writer: writer,
		stream: cipher.NewCFBEncrypter(cph, PKCS5Padding([]byte(key), aes.BlockSize)),
	}, nil
}

// NewDecrypt create an aes-decrypt stream
func NewDecrypt(key string, reader io.Reader) (*Aes, error) {
	cph, err := NewCipher(key)
	if err != nil {
		return nil, err
	}

	return &Aes{
		key:    key,
		reader: reader,
		stream: cipher.NewCFBDecrypter(cph, PKCS5Padding([]byte(key), aes.BlockSize)),
	}, nil
}

// NewCipher create a aes-cipher
func NewCipher(key string) (cipher.Block, error) {
	return aes.NewCipher(PKCS5Padding([]byte(key), aes.BlockSize))
}

// PKCS5Padding padding some bytes into s
func PKCS5Padding(s []byte, size int) []byte {
	padding := size - (len(s) % size)
	return append(s, bytes.Repeat([]byte{byte(padding)}, padding)...)
}
