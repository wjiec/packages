package main

import (
	"context"
	"os/exec"
	"protob/internal/home"
	"protob/internal/subcommand"
	"runtime"
	"time"

	"github.com/spf13/cobra"
)

var (
	Version     = "0.0.0"
	GitRevision = "0000000"
	BuildTime   = time.Now().Format("2006/01/02")
)

func main() {
	root := cobra.Command{Use: "protob"}

	root.AddCommand(subcommand.Compile())
	root.AddCommand(subcommand.Install())
	root.AddCommand(subcommand.Version(Version, GitRevision, BuildTime))

	s := &home.State{DependencyPath: home.ExpandDir("include")}
	root.PersistentPreRun = func(cmd *cobra.Command, args []string) {
		s.EmbeddedCompiler = home.ExpandDir("protoc")
		if runtime.GOOS == "windows" {
			s.EmbeddedCompiler += ".exe"
		}

		if path, err := exec.LookPath("protoc"); err == nil {
			s.SystemCompiler = path
		}
	}

	ctx, _ := context.WithCancel(context.WithValue(context.Background(), "home", s))
	_ = root.ExecuteContext(ctx)
}
