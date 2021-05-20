Go - gRPC
---------

gRPC是谷歌基于Protobuf开发的跨语言的开源RPC框架。gRPC基于HTTP/2协议涉及，可以基于一个HTTP/2链接提供多个服务，对移动设备更加友好。


### 普通证书认证

gRPC建立在HTTP/2协议之上，对TLS提供了很好的帮助。可以通过openssl工具生成证书文件，通过`credentials.NewServerTLSFromFile(...)`方法构造证书对象，然后通过`grpc.Creds`函数将证书包装为参数传入`grpc.NewServer`方法启用服务端证书认证。

在客户端可以通过`credentials.NewClientTLSFromFile(...)`并将其通过`grpc.WithTransportCredentials(...)`方法开启对服务端证书和服务器名字进行认证。


### CA方式认证

在复杂网络环境中，直接传递证书也是非常危险的，此时中间人可以进行证书的监听和替换。为了避免证书在传递过程中被篡改，可以通过一个安全可靠的根证书分别对服务器和客户端证书进行签名，这样客户端和服务端就可以在收到对方的证书之后通过根证书验证证书的有效性。

在CA方式客户端代码中，我们不直接依赖服务端证书文件，而是在`credentials.NewTLS(...)`方法中引入一个CA根证书和服务器名字对接收到服务器证书进行验证。

在CA方式服务端代码中，服务端也可以对客户端证书进行认证，也是在`credentials.NewTLS(...)`方法中引入一个CA根证书并通过`ClientAuth`选项启用客户端认证。


### 基于Token方式的认证

gRPC还可以为每个方法调用提供认证支持，这样就可以基于用户Token对不同的方式访问进行权限管理。

要实现对每个gRPC方法进行认证，需要实现`grpc.PerRPCCredentials`接口。通过`GetRequestMetadata`方法返回认证所必要的信息。`RequireTransportSecurity`表示是否要求底层启用安全连接。
```go
type PerRPCCredentials interface {
	// GetRequestMetadata gets the current request metadata, refreshing
	// tokens if required. This should be called by the transport layer on
	// each request, and the data should be populated in headers or other
	// context. If a status code is returned, it will be used as the status
	// for the RPC. uri is the URI of the entry point for the request.
	// When supported by the underlying implementation, ctx can be used for
	// timeout and cancellation. Additionally, RequestInfo data will be
	// available via ctx to this call.
	// TODO(zhaoq): Define the set of the qualified keys instead of leaving
	// it as an arbitrary string.
	GetRequestMetadata(ctx context.Context, uri ...string) (map[string]string, error)
	// RequireTransportSecurity indicates whether the credentials requires
	// transport security.
	RequireTransportSecurity() bool
}
```
最后通过`grpc.WithPerRPCCredentials`方法将认证对象传入`grpc.Dial`中启用基于Token的认证。

服务端通过`metdata.FromIncomingContext`方法从上下文获取元数据并进行认证。如果认证失败可以返回一个`codes.Unauthenticated`错误。


### 拦截器

gRPC中提供`grpc.UnaryInterceptor`和`grpc.StreamInterceptor`分别对普通方法和流方法提供拦截器的支持。

由于gRPC框架中只能为每个服务设置一个拦截器，因此所有的拦截操作都要在一个函数中完成。可以参考`grpc-ecosystem`中`go-grpc-middleware`包对于gRPC拦截器提供的链式的拦截器实现。


### 和Web服务共存

gRPC构建于HTTP/2协议之上，所以我们可以将grpc服务和普通的Web服务架设在同一个端口之上。gRPC服务已经支持实现了`g.ServeHTTP`方法。所以直接在`http.HandlerFunc`中根据是否是http2或者`content-type`是否等于`application/grpc`决定是走正常的http流程还是走`g.ServeHTTP`流程。
