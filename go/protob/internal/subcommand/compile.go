package subcommand

import "github.com/spf13/cobra"

func Compile() *cobra.Command {
	return &cobra.Command{
		Use:   "compile",
		Short: "Compile Protobuf file",
		Run: func(cmd *cobra.Command, args []string) {
		},
	}
}
