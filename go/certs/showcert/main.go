package main

import (
	"crypto/tls"
	"fmt"
)

func main() {
	var cfg tls.Config
	conn, err := tls.Dial("tcp", "apple.com.cn:443", &cfg)
	if err != nil {
		panic(err)
	}

	certs := conn.ConnectionState().PeerCertificates
	for idx, cert := range certs {
		fmt.Printf("%d: O=%s, CN=%s\n", idx, cert.Subject.Organization, cert.Subject.CommonName)
	}
}
