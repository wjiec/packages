package netrpc

import (
	"fmt"

	"google.golang.org/protobuf/compiler/protogen"
)

func GenerateFileContent(gen *protogen.Plugin, file *protogen.File, g *protogen.GeneratedFile) {
	if len(file.Services) > 0 {
		g.QualifiedGoIdent(protogen.GoIdent{
			GoName:       "rpc",
			GoImportPath: "net/rpc",
		})

		for _, srv := range file.Services {
			genRpcService(srv, g)
		}
	}
}

func genRpcService(srv *protogen.Service, g *protogen.GeneratedFile) {
	genServiceInterface(srv, g)
	genServiceClient(srv, g)
	genServiceDialer(srv, g)
}

func genServiceInterface(srv *protogen.Service, g *protogen.GeneratedFile) {
	g.P(fmt.Sprintf("type %s interface {", srv.GoName))
	for _, method := range srv.Methods {
		g.P(fmt.Sprintf("%s(%s, *%s) error", method.GoName, method.Input.Desc.Name(), method.Output.Desc.Name()))
	}
	g.P("}")
}

func genServiceClient(srv *protogen.Service, g *protogen.GeneratedFile) {
	g.P(fmt.Sprintf("type %sClient struct {", srv.GoName))
	g.P("c *rpc.Client")
	g.P("}")
	g.P()
	g.P(fmt.Sprintf("var _ *%sClient = (*%sClient)(nil)", srv.GoName, srv.GoName))
	g.P()
	for _, method := range srv.Methods {
		g.P(fmt.Sprintf("func (srv *%sClient) %s(in %s, out *%s) error {", srv.GoName, method.GoName,
			method.Input.Desc.Name(), method.Output.Desc.Name()))
		g.P(fmt.Sprintf(`return srv.c.Call("%s.%s", in, out)`, srv.GoName, method.GoName))
		g.P("}")
	}
	g.P()
	g.P(fmt.Sprintf("func Dial%s(network, address string) (*%sClient, error) {", srv.GoName, srv.GoName))
	g.P("c, err := rpc.Dial(network, address)")
	g.P("if err != nil {")
	g.P("return nil, err")
	g.P("}")
	g.P(fmt.Sprintf("return &%sClient{c: c}, nil", srv.GoName))
	g.P("}")
}

func genServiceDialer(srv *protogen.Service, g *protogen.GeneratedFile) {
	g.P()
	g.P(fmt.Sprintf("func Register%s(srv *rpc.Server, v %s) error {", srv.GoName, srv.GoName))
	g.P(fmt.Sprintf(`if err := srv.RegisterName("%s", v); err != nil {`, srv.GoName))
	g.P("return err")
	g.P("}")
	g.P("return nil")
	g.P("}")
}
