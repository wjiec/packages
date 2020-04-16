package command

import (
	"assembler/pkg/commander"
	"context"
	"flag"
	"fmt"
	"os"
)

// Disassemble represents a disassemble-command
type Disassemble struct {
	output   string
	password string
}

// Name returns name of the assemble-command
func (d *Disassemble) Name() string {
	return "disassemble"
}

// Synopsis returns a short string (less than one line) describing the command
func (d *Disassemble) Synopsis() string {
	return "disassemble a archive into a directory"
}

// SetFlags add flags to commands
func (d *Disassemble) SetFlags(f *flag.FlagSet) {
	f.StringVar(&d.output, "output", "", "output directory")
	f.StringVar(&d.output, "o", "", "output directory(shorted)")

	f.StringVar(&d.password, "key", "", "key with encrypt")
	f.StringVar(&d.password, "k", "", "key with encrypt")
}

// Execute executes the command and returns an ExitStatus
func (d *Disassemble) Execute(ctx context.Context, f *flag.FlagSet, args ...interface{}) commander.ExitStatus {
	if f.NArg() < 1 {
		_, _ = fmt.Fprint(os.Stderr, "unknown input archive")
		return commander.ExitUsageError
	}

	fmt.Println(d)

	return commander.ExitSuccess
}
