package main

import (
	"advanced-go/23.go-protobuf/protoc-gen-go-netrpc/netrpc"
	gengo "google.golang.org/protobuf/cmd/protoc-gen-go/internal_gengo"
	"google.golang.org/protobuf/compiler/protogen"
)

func main() {
	protogen.Options{
		ParamFunc: nil,
		ImportRewriteFunc: func(importPath protogen.GoImportPath) protogen.GoImportPath {
			return importPath
		},
	}.Run(func(gen *protogen.Plugin) error {
		for _, f := range gen.Files {
			if f.Generate {
				g := gengo.GenerateFile(gen, f)
				netrpc.GenerateFileContent(gen, f, g)
			}
		}
		gen.SupportedFeatures = gengo.SupportedFeatures
		return nil
	})
}
