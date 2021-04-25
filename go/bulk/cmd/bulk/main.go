package main

import (
	"bulk/internnal/bulk/crlf"
	"bulk/pkg/contexts"
	"context"
	"fmt"
	"os"

	"github.com/spf13/cobra"
)

func main() {
	root := cobra.Command{Use: "bulk", Short: "A bulk tools", SilenceUsage: true, SilenceErrors: true}

	root.AddCommand(crlf.Command())
	root.SetFlagErrorFunc(func(command *cobra.Command, e error) error {
		fmt.Println(command, e)
		return e
	})

	if err := root.ExecuteContext(contexts.WithExitSignal(context.Background())); err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "fatal: %s\n", err)
	}
}
