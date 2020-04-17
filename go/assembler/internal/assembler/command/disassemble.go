package command

import (
	"assembler/pkg/aes"
	"assembler/pkg/commander"
	"assembler/pkg/tar"
	"compress/gzip"
	"context"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
)

// Disassemble represents a disassemble-command
type Disassemble struct {
	debug    bool
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

	f.BoolVar(&d.debug, "debug", false, "enable debug")
}

// Execute executes the command and returns an ExitStatus
func (d *Disassemble) Execute(ctx context.Context, f *flag.FlagSet, args ...interface{}) commander.ExitStatus {
	if f.NArg() < 1 {
		_, _ = fmt.Fprint(os.Stderr, "unknown input archive")
		return commander.ExitUsageError
	}

	if !d.debug {
		log.SetOutput(ioutil.Discard)
	} else {
		log.SetPrefix("")
		log.SetFlags(log.Lmsgprefix)
	}

	input := f.Arg(0)
	if _, err := os.Stat(input); err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unable to found archive: %s\n", err)
		return commander.ExitUsageError
	}

	var reader io.Reader

	file, err := os.Open(input)
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unable to load archive: %s\n", err)
		return commander.ExitUsageError
	}
	reader = file

	if len(d.password) != 0 {
		if reader, err = aes.NewDecrypt(d.password, reader); err != nil {
			_, _ = fmt.Fprintf(os.Stderr, "unable to create aes-cipher: %s\n", err)
			return commander.ExitFailure
		}
	}

	gz, err := gzip.NewReader(reader)
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unable to unzip archive: %s\n", err)
		return commander.ExitFailure
	}
	reader = gz

	if len(d.output) == 0 {
		_, _ = fmt.Fprintln(os.Stderr, "unknown output dir")
		return commander.ExitUsageError
	}

	if info, err := os.Stat(d.output); err == nil {
		if os.IsExist(err) && !info.IsDir() {
			_, _ = fmt.Fprintf(os.Stderr, "unable to extra archive into %s: %s\n", d.output, err)
			return commander.ExitFailure
		}
	}

	archive := tar.NewReader(reader)
	if err := archive.Extra(d.output); err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unzip archive failure: %s\n", err)
		return commander.ExitFailure
	}

	return commander.ExitSuccess
}
