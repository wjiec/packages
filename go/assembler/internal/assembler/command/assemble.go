package command

import (
	"assembler/pkg/commander"
	"context"
	"flag"
	"fmt"
	"os"
)

// Assemble represents a assemble-command
type Assemble struct {
	output   string
	password string
}

// Name returns name of the assemble-command
func (a *Assemble) Name() string {
	return "assemble"
}

// Synopsis returns a short string (less than one line) describing the command
func (a *Assemble) Synopsis() string {
	return "assemble a archive from files"
}

// SetFlags add flags to commands
func (a *Assemble) SetFlags(f *flag.FlagSet) {
	f.StringVar(&a.output, "output", "", "output archive file")
	f.StringVar(&a.output, "o", "", "output archive file(shorted)")

	f.StringVar(&a.password, "key", "", "key with encrypt")
	f.StringVar(&a.password, "k", "", "key with encrypt")
}

// Execute executes the command and returns an ExitStatus
func (a *Assemble) Execute(ctx context.Context, f *flag.FlagSet, args ...interface{}) commander.ExitStatus {
	if f.NArg() < 1 {
		_, _ = fmt.Fprint(os.Stderr, "unknown input files")
		return commander.ExitUsageError
	}

	if len(a.output) == 0 {
		_, _ = fmt.Fprint(os.Stderr, "unknown output archive")
		return commander.ExitUsageError
	}

	fmt.Println(a)

	return commander.ExitSuccess
}
