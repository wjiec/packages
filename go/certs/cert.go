package main

import (
	"bytes"
	"crypto"
	"crypto/ecdsa"
	"crypto/elliptic"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha1"
	"crypto/x509"
	"crypto/x509/pkix"
	"encoding/asn1"
	"encoding/pem"
	"errors"
	"fmt"
	"io"
	"math/big"
	"net"
	"net/mail"
	"net/url"
	"time"

	"software.sslmate.com/src/go-pkcs12"
)

// ref. https://github.com/notaryproject/notary/blob/master/tuf/utils/x509.go

func GeneratePrivate(bits int) (crypto.Signer, error) {
	switch bits {
	case 224:
		return ecdsa.GenerateKey(elliptic.P224(), rand.Reader)
	case 256:
		return ecdsa.GenerateKey(elliptic.P256(), rand.Reader)
	case 384:
		return ecdsa.GenerateKey(elliptic.P384(), rand.Reader)
	case 521:
		return ecdsa.GenerateKey(elliptic.P521(), rand.Reader)
	case 2048, 3072, 4096:
		return rsa.GenerateKey(rand.Reader, bits)
	default:
		return rsa.GenerateKey(rand.Reader, 2048)
	}
}

type SubjectPublicKeyInfo struct {
	Algorithm        pkix.AlgorithmIdentifier
	SubjectPublicKey asn1.BitString
}

// PrivateKey represents a private key using an unspecified algorithm.
type PrivateKey interface {
	// Public returns the public key corresponding to the opaque,
	// private key.
	Public() crypto.PublicKey
}

func SubjectKeyId(priv PrivateKey) ([]byte, error) {
	pubASN1, err := x509.MarshalPKIXPublicKey(priv.Public())
	if err != nil {
		return nil, err
	}

	var subjectPublicKeyInfo SubjectPublicKeyInfo
	if _, err = asn1.Unmarshal(pubASN1, &subjectPublicKeyInfo); err != nil {
		return nil, err
	}

	skid := sha1.Sum(subjectPublicKeyInfo.SubjectPublicKey.Bytes)
	return skid[:], nil
}

func RandomSerialNumber() (*big.Int, error) {
	serialNumberLimit := new(big.Int).Lsh(big.NewInt(1), 128)
	return rand.Int(rand.Reader, serialNumberLimit)
}

func NewCertificate(priv PrivateKey, org, orgUnit, cn string) ([]*x509.Certificate, error) {
	serialNumber, err := RandomSerialNumber()
	if err != nil {
		return nil, err
	}

	skid, err := SubjectKeyId(priv)
	if err != nil {
		return nil, err
	}

	template := &x509.Certificate{
		SerialNumber: serialNumber,
		Subject: pkix.Name{
			Organization:       []string{org},
			OrganizationalUnit: []string{orgUnit},

			// The CommonName is required by iOS to show the certificate in the
			// "Certificate Trust Settings" menu.
			// https://github.com/FiloSottile/mkcert/issues/47
			CommonName: cn,
		},
		SubjectKeyId: skid,

		NotBefore: time.Now(),
		NotAfter:  time.Now().AddDate(100, 0, 0),

		KeyUsage: x509.KeyUsageCertSign,

		BasicConstraintsValid: true,
		IsCA:                  true,
		MaxPathLenZero:        true,
	}

	der, err := x509.CreateCertificate(rand.Reader, template, template, priv.Public(), priv)
	if err != nil {
		return nil, err
	}

	return x509.ParseCertificates(der)
}

func DumpPrivate(pk crypto.PrivateKey) ([]byte, error) {
	der, err := x509.MarshalPKCS8PrivateKey(pk)
	if err != nil {
		return nil, err
	}

	return pem.EncodeToMemory(&pem.Block{Type: "PRIVATE KEY", Bytes: der}), nil
}

func DumpCertificate(cert *x509.Certificate) ([]byte, error) {
	return pem.EncodeToMemory(&pem.Block{Type: "CERTIFICATE", Bytes: cert.Raw}), nil
}

func DumpCertificateChain(certs []*x509.Certificate) ([]byte, error) {
	var buf bytes.Buffer
	for _, cert := range certs {
		if err := pem.Encode(&buf, &pem.Block{Type: "CERTIFICATE", Bytes: cert.Raw}); err != nil {
			return nil, err
		}
	}
	return buf.Bytes(), nil
}

func LoadCertificate(r io.Reader) ([]*x509.Certificate, error) {
	content, err := io.ReadAll(r)
	if err != nil {
		return nil, err
	}

	der, _ := pem.Decode(content)
	if der == nil || der.Type != "CERTIFICATE" {
		return nil, errors.New("failed to read certificate: unexpected content")
	}

	return x509.ParseCertificates(der.Bytes)
}

func LoadCertBundleFromPEM(r io.Reader) ([]*x509.Certificate, error) {
	content, err := io.ReadAll(r)
	if err != nil {
		return nil, err
	}

	var certs []*x509.Certificate

	block, rest := pem.Decode(content)
	for ; block != nil; block, rest = pem.Decode(rest) {
		if block.Type == "CERTIFICATE" {
			cert, err := x509.ParseCertificates(block.Bytes)
			if err != nil {
				return nil, err
			}
			certs = append(certs, cert...)
		}
	}

	if len(certs) == 0 {
		return nil, fmt.Errorf("no valid certificates found")
	}

	return certs, nil
}

func LoadPrivate(r io.Reader) (crypto.PrivateKey, error) {
	content, err := io.ReadAll(r)
	if err != nil {
		return nil, err
	}

	der, _ := pem.Decode(content)
	if der == nil || der.Type != "PRIVATE KEY" {
		return nil, errors.New("failed to read key: unexpected content")
	}

	return x509.ParsePKCS8PrivateKey(der.Bytes)
}

func SignCertificate(parentKey crypto.PrivateKey, parentCert *x509.Certificate, certKey crypto.PrivateKey, hosts []string) ([]*x509.Certificate, error) {
	serialNumber, err := RandomSerialNumber()
	if err != nil {
		return nil, err
	}

	template := &x509.Certificate{
		SerialNumber: serialNumber,
		Subject: pkix.Name{
			Organization:       []string{"@TODO"},
			OrganizationalUnit: []string{"@TODO"},
		},

		NotBefore: time.Now(),
		// Certificates last for 2 years and 3 months, which is always less than
		// 825 days, the limit that macOS/iOS apply to all certificates,
		// including custom roots. See https://support.apple.com/en-us/HT210176.
		NotAfter: time.Now().AddDate(2, 3, 0),

		KeyUsage: x509.KeyUsageKeyEncipherment | x509.KeyUsageDigitalSignature,
	}

	for _, host := range hosts {
		if ip := net.ParseIP(host); ip != nil {
			template.IPAddresses = append(template.IPAddresses, ip)
		} else if email, err := mail.ParseAddress(host); err == nil && email.Address == host {
			template.EmailAddresses = append(template.EmailAddresses, host)
		} else if uriName, err := url.Parse(host); err == nil && uriName.Scheme != "" && uriName.Host != "" {
			template.URIs = append(template.URIs, uriName)
		} else {
			template.DNSNames = append(template.DNSNames, host)
		}
	}

	//if client {
	//	template.ExtKeyUsage = append(template.ExtKeyUsage, x509.ExtKeyUsageClientAuth)
	//}
	if len(template.IPAddresses) > 0 || len(template.DNSNames) > 0 || len(template.URIs) > 0 {
		template.ExtKeyUsage = append(template.ExtKeyUsage, x509.ExtKeyUsageServerAuth)
	}
	if len(template.EmailAddresses) > 0 {
		template.ExtKeyUsage = append(template.ExtKeyUsage, x509.ExtKeyUsageEmailProtection)
	}

	// IIS (the main target of PKCS #12 files), only shows the deprecated
	// Common Name in the UI. See issue #115.
	//if pkcs12 {
	//	template.Subject.CommonName = hosts[0]
	//}

	cert, err := x509.CreateCertificate(rand.Reader, template, parentCert, certKey.(crypto.Signer).Public(), parentKey)
	if err != nil {
		return nil, err
	}

	return x509.ParseCertificates(cert)
}

func DumpPKCS12FromCertificate(pk crypto.PrivateKey, cert, caCert *x509.Certificate, password string) ([]byte, error) {
	pfxData, err := pkcs12.Encode(rand.Reader, pk, cert, []*x509.Certificate{caCert}, password)
	if err != nil {
		return nil, err
	}

	return pfxData, nil
}

func FromCertificateRequest(parentKey crypto.PrivateKey, parentCert *x509.Certificate, r io.Reader) ([]*x509.Certificate, error) {
	csrContent, err := io.ReadAll(r)
	if err != nil {
		return nil, err
	}

	csrPEM, _ := pem.Decode(csrContent)
	if csrPEM == nil {
		return nil, errors.New("failed to read CSR: unexpected content")
	}
	if csrPEM.Type != "CERTIFICATE REQUEST" && csrPEM.Type != "NEW CERTIFICATE REQUEST" {
		return nil, errors.New("failed to read CSR: excepted CERTIFICATE REQUEST, got " + csrPEM.Type)
	}

	csr, err := x509.ParseCertificateRequest(csrPEM.Bytes)
	if err != nil {
		return nil, err
	}

	serialNumber, err := RandomSerialNumber()
	if err != nil {
		return nil, err
	}

	template := &x509.Certificate{
		SerialNumber:    serialNumber,
		Subject:         csr.Subject,
		ExtraExtensions: csr.ExtraExtensions,

		NotBefore: time.Now(),
		// Certificates last for 2 years and 3 months, which is always less than
		// 825 days, the limit that macOS/iOS apply to all certificates,
		// including custom roots. See https://support.apple.com/en-us/HT210176.
		NotAfter: time.Now().AddDate(2, 3, 0),

		DNSNames: []string{csr.Subject.CommonName},

		// Likewise, if the CSR does not set KUs and EKUs, fix it up as Apple
		// platforms require serverAuth for TLS.
		KeyUsage:    x509.KeyUsageKeyEncipherment | x509.KeyUsageDigitalSignature,
		ExtKeyUsage: []x509.ExtKeyUsage{x509.ExtKeyUsageServerAuth},
	}

	//if client {
	//	template.ExtKeyUsage = append(template.ExtKeyUsage, x509.ExtKeyUsageClientAuth)
	//}

	if len(csr.EmailAddresses) > 0 {
		template.ExtKeyUsage = append(template.ExtKeyUsage, x509.ExtKeyUsageEmailProtection)
	}

	der, err := x509.CreateCertificate(rand.Reader, template, parentCert, csr.PublicKey, parentKey)
	if err != nil {
		return nil, err
	}

	return x509.ParseCertificates(der)
}

func LoadCertificateHosts(cert *x509.Certificate) []string {
	var hosts []string
	hosts = append(hosts, cert.DNSNames...)
	hosts = append(hosts, cert.EmailAddresses...)
	for _, ip := range cert.IPAddresses {
		hosts = append(hosts, ip.String())
	}
	for _, uri := range cert.URIs {
		hosts = append(hosts, uri.String())
	}

	return hosts
}

const (
	MinRSABitSize = 2048
)

// ValidateCertificate returns an error if the certificate is not valid for notary
// Currently this is only ensuring the public key has a large enough modulus if RSA,
// using a non SHA1 signature algorithm, and an optional time expiry check
func ValidateCertificate(cert *x509.Certificate) error {
	if cert.NotBefore.After(cert.NotAfter) {
		return fmt.Errorf("certificate validity window is invalid")
	}

	now := time.Now()
	if now.Before(cert.NotBefore) || now.After(cert.NotAfter) {
		return fmt.Errorf("certificate with CN %s is expired", cert.Subject.CommonName)
	}

	// Can't have SHA1 signature algorithm
	switch cert.SignatureAlgorithm {
	case x509.SHA1WithRSA, x509.DSAWithSHA1, x509.ECDSAWithSHA1:
		return fmt.Errorf("certificate with CN %s used invalid SHA1 signature algorithm", cert.Subject.CommonName)
	}

	// If we have an RSA key, make sure it's long enough
	if cert.PublicKeyAlgorithm == x509.RSA {
		pubKey, ok := cert.PublicKey.(*rsa.PublicKey)
		if !ok {
			return fmt.Errorf("unable to parse RSA public key")
		}
		if pubKey.N.BitLen() < MinRSABitSize {
			return fmt.Errorf("bit length of the RSA is too short")
		}
	}

	return nil
}
