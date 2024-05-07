package main

import (
	"os"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestGeneratePrivate(t *testing.T) {
	pk, err := GeneratePrivate(4096)
	if assert.NoError(t, err) {
		assert.NotNil(t, pk)
		assert.NotNil(t, pk.Public())
	}
}

func TestSubjectKeyId(t *testing.T) {
	if pk, err := GeneratePrivate(4096); assert.NoError(t, err) {
		skid, err := SubjectKeyId(pk)
		if assert.NoError(t, err) {
			assert.NotNil(t, skid)
		}
	}
}

func TestDumpPrivate(t *testing.T) {
	if pk, err := GeneratePrivate(4096); assert.NoError(t, err) {
		pem, err := DumpPrivate(pk)
		if assert.NoError(t, err) {
			t.Logf("Dump Private Key:\n%s", pem)
		}
	}
}

func TestDumpCertificate(t *testing.T) {
	if pk, err := GeneratePrivate(4096); assert.NoError(t, err) {
		cert, err := NewCertificate(pk, "testing", "testing - 123", "testing")
		if assert.NoError(t, err) {
			pem, err := DumpCertificate(cert[0])
			if assert.NoError(t, err) {
				t.Logf("Dump Certificate Key:\n%s", pem)
			}

			pkPem, _ := DumpPrivate(pk)
			_ = os.WriteFile("xx.key", pkPem, 0400)
			_ = os.WriteFile("xx.cert", pem, 0644)
		}
	}
}
