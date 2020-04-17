package command

import (
	"assembler/pkg/aes"
	"assembler/pkg/commander"
	"assembler/pkg/tar"
	"bufio"
	"bytes"
	"compress/gzip"
	"context"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"
)

// Assemble represents a assemble-command
type Assemble struct {
	debug    bool
	output   string
	password string
	excludes string
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

	f.StringVar(&a.excludes, "exclude", "", "exclude files")
	f.StringVar(&a.excludes, "e", "", "exclude files(shorted)")

	f.BoolVar(&a.debug, "debug", false, "enable debug")
}

// Stream represents a bytes stream
type Stream interface {
	// Pipeline connect pipeline
	Pipeline(<-chan []byte) <-chan []byte
}

// Execute executes the command and returns an ExitStatus
func (a *Assemble) Execute(ctx context.Context, f *flag.FlagSet, args ...interface{}) commander.ExitStatus {
	if f.NArg() < 1 {
		_, _ = fmt.Fprintln(os.Stderr, "unknown input files")
		return commander.ExitUsageError
	}

	if !a.debug {
		log.SetOutput(ioutil.Discard)
	} else {
		log.SetPrefix("")
		log.SetFlags(log.Lmsgprefix)
	}

	if len(a.output) == 0 {
		_, _ = fmt.Fprintln(os.Stderr, "unknown output archive")
		return commander.ExitUsageError
	}

	var err error
	var writer io.Writer

	buffer := new(bytes.Buffer)
	writer = buffer
	if len(a.password) != 0 {
		if writer, err = aes.NewEncrypt(a.password, writer); err != nil {
			_, _ = fmt.Fprintf(os.Stderr, "unable to create aes-cipher: %s\n", err)
			return commander.ExitFailure
		}
	}

	gz, _ := gzip.NewWriterLevel(writer, gzip.BestCompression)
	writer = gz

	archive := tar.NewWriter(writer, strings.Split(a.excludes, ","))
	for _, file := range f.Args() {
		abs, err := filepath.Abs(file)
		if err != nil {
			log.Printf("unable to abs path: %s\n", err)
			return commander.ExitFailure
		}

		if err := archive.Add(abs, ""); err != nil {
			log.Printf("unable to add file: %s\n", err)
			return commander.ExitFailure
		}
	}

	file, err := os.Create(a.output)
	if err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "unable to create archive: %s\n", err)
		return commander.ExitFailure
	}

	defer func() {
		_ = file.Close()
	}()

	_ = archive.Flush()
	_ = gz.Close()

	bFile := bufio.NewWriter(file)
	if _, err = io.Copy(bFile, buffer); err != nil {
		log.Printf("unable to write file: %s\n", err)
		return commander.ExitFailure
	}

	_ = bFile.Flush()
	return commander.ExitSuccess
}
