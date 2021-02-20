package main

import (
	"context"
	"protob/internal/subcommand"
	"protob/pkg/logging"
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

	root.PersistentFlags().StringP("config", "c", "~/.protob.json", "specify config file")
	root.PersistentPreRun = func(cmd *cobra.Command, args []string) {
		logging.Error("config file not found")
	}

	ctx, _ := context.WithCancel(context.WithValue(context.Background(), "config", nil))
	_ = root.ExecuteContext(ctx)
}
