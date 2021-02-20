package subcommand

import (
	"fmt"

	"github.com/spf13/cobra"
)

func Install() *cobra.Command {
	return &cobra.Command{
		Use:   "install",
		Short: "Install new version of Protobuf",
		Run: func(cmd *cobra.Command, args []string) {
			fmt.Println(args)
		},
	}
}
