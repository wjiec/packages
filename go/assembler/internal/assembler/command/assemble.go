package command

import (
	"assembler/pkg/aes"
	"assembler/pkg/commander"
	"assembler/pkg/gzip"
	"assembler/pkg/tar"
	"context"
	"flag"
	"fmt"
	"io"
	"log"
	"os"
	"path"
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

	file, err := os.Create(a.output)
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unable to create archive: %s", err)
		return commander.ExitUsageError
	}

	var reader io.Reader

	archive := tar.New(path.Base(a.output))
	for _, file := range f.Args() {
		if err := archive.Add(file, "/"); err != nil {
			log.Printf("unable to add file: %s\n", err)
			return commander.ExitFailure
		}
	}
	reader = archive

	gz := gzip.New()
	if err := archive.WriteTo(gz); err != nil {
		log.Printf("unable to compress archive: %s\n", err)
		return commander.ExitFailure
	}
	reader = gz

	if len(a.password) != 0 {
		cipher, err := aes.New(a.password)
		if err != nil {
			log.Printf("unable to create cipher: %s\n", err)
			return commander.ExitFailure
		}

		if err := gz.WriteTo(cipher); err != nil {
			log.Printf("unable to encrypted: %s\n", err)
			return commander.ExitFailure
		}
		reader = cipher
	}

	if _, err := io.Copy(file, reader); err != nil {
		log.Printf("unable to written: %s\n", err)
		return commander.ExitFailure
	}

	return commander.ExitSuccess
}
