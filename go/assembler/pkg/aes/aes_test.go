package aes

import (
	"crypto/aes"
	"crypto/cipher"
	"fmt"
	"testing"
)

func TestNew(t *testing.T) {
	block, err := aes.NewCipher(PKCS5Padding([]byte("duomai.com"), aes.BlockSize))
	if err != nil {
		t.Error(err)
	}

	//cbc := cipher.NewCBCEncrypter(block, PKCS5Padding([]byte("duomai.com"), block.BlockSize()))
	cfb := cipher.NewCFBEncrypter(block, PKCS5Padding([]byte("duomai.com"), block.BlockSize()))

	dst := make([]byte, len("duomai.com"))
	cfb.XORKeyStream(dst, []byte("duomai.com"))
	fmt.Printf("%+v\n", dst)
}
