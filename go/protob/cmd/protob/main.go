package main

import (
	"context"
	"fmt"
	"os"

	"github.com/spf13/cobra"
)

func main() {
	root := cobra.Command{Use: "protob"}

	if err := root.ExecuteContext(context.Background()); err != nil {
		_, _ = fmt.Fprintln(os.Stderr, "error: %w", err)
	}
}
