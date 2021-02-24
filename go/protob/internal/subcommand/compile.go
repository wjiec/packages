package subcommand

import (
	"fmt"
	"protob/internal/home"
	"protob/pkg/logging"

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
			s := cmd.Context().Value(flagUsingSystemCompiler).(*home.State)
			if len(args) == 0 {
				logging.Fatal("no input files")
			}

			sys, _ := cmd.PersistentFlags().GetBool(flagUsingSystemCompiler)
			compiler := s.UsableCompiler(sys)
			fmt.Println(compiler)
		},
	}

	cmd.PersistentFlags().Bool(flagUsingSystemCompiler, false, "using system compiler")

	return cmd
}
