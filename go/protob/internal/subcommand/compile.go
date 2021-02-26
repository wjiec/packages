package subcommand

import (
	"github.com/spf13/cobra"
)

const (
	flagUsingSystemCompiler = "sys"
)

func Compile() *cobra.Command {
	cmd := &cobra.Command{
		Use:   "compile",
		Short: "Compile Protobuf files",
		Run: func(cmd *cobra.Command, args []string) {
		},
	}

	cmd.PersistentFlags().Bool(flagUsingSystemCompiler, false, "using system compiler")

	return cmd
}
