package echo

import (
	"bytes"
	"io"
	"log"
	"net"
	"net/http"
	"net/rpc"
	"net/rpc/jsonrpc"
	"sync"
	"time"
)

const serviceName = "simple/echo.EchoService"

type Service interface {
	Echo(string, *string) error
}

func RegisterEchoServiceServer(service Service) error {
	return rpc.RegisterName(serviceName, service)
}

type ServiceClient struct {
	c       *rpc.Client
	network string
	address string
}

func (srv *ServiceClient) Close() {
	if srv.c != nil {
		if err := srv.c.Close(); err != nil {
			panic(err)
		}
		srv.c = nil
	}
}

// 增加编译守卫, 在编译器检查(*ServiceClient)是否实现了Service接口
// 未实现则会直接在编译器报错
var _ Service = (*ServiceClient)(nil)

func (srv *ServiceClient) Echo(s string, v *string) error {
	return srv.c.Call(serviceName+".Echo", s, v)
}

type DialOption func(*ServiceClient)

func DialRawEchoService(network, addr string, options ...DialOption) *ServiceClient {
	c, err := rpc.Dial(network, addr)
	if err != nil {
		panic(err)
	}

	client := &ServiceClient{c: c, network: network, address: addr}
	for _, opt := range options {
		opt(client)
	}
	return client
}

func WithJsonCodec() DialOption {
	return func(client *ServiceClient) {
		conn, err := net.Dial(client.network, client.address)
		if err != nil {
			panic(err)
		}

		client.Close()
		client.c = rpc.NewClientWithCodec(jsonrpc.NewClientCodec(conn))
	}
}

func WithHttpCodec(path string) DialOption {
	return func(client *ServiceClient) {
		client.Close()
		client.c = rpc.NewClientWithCodec(jsonrpc.NewClientCodec(&httpConn{addr: client.address, path: path}))
	}
}

type httpConn struct {
	sync.Mutex
	io.ReadCloser
	ch         chan io.ReadCloser
	addr, path string
}

func (hc *httpConn) Read(p []byte) (n int, err error) {
	log.Printf("httpclient: read")

	for {
		hc.Lock()
		if hc.ReadCloser != nil {
			n, err = hc.ReadCloser.Read(p)
			if err == io.EOF {
				err = hc.Close()
			}

			hc.Unlock()
			return n, err
		}

		hc.Unlock()
		time.Sleep(50 * time.Millisecond)
	}
}

func (hc *httpConn) Write(p []byte) (n int, err error) {
	log.Printf("httpclient: write")

	resp, err := http.Post("http://"+hc.addr+hc.path, "application/json", bytes.NewReader(p))
	if err != nil {
		return 0, err
	}
	hc.Lock()
	hc.ReadCloser = resp.Body
	hc.Unlock()

	return len(p), nil
}

func (hc *httpConn) Close() error {
	log.Printf("httpclient: closed")

	defer func() { hc.ReadCloser = nil }()
	if hc.ReadCloser == nil {
		return nil
	}
	return hc.ReadCloser.Close()
}
