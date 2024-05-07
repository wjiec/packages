package trusted

import "crypto/x509"

type Certificate struct {
	*x509.Certificate
}
