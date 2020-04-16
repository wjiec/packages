package commander

import (
	"context"
	"errors"
	"flag"
	"fmt"
	"io"
	"os"
	"path"
)

var (
	ExistCommander = errors.New("commander exists")
)

// ExitStatus represents a posix exit status
type ExitStatus int

const (
	ExitSuccess ExitStatus = iota
	ExitFailure
	ExitUsageError
)

// Commander represents a commander
type Commander interface {
	// Name name of the commander
	Name() string

	// Synopsis returns a short string (less than one line) describing the command
	Synopsis() string

	// SetFlags add flags to commands
	SetFlags(*flag.FlagSet)

	// Execute executes the command and returns an ExitStatus
	Execute(ctx context.Context, f *flag.FlagSet, args ...interface{}) ExitStatus
}

// CommandLine represents a command-line
type CommandLine struct {
	Output io.Writer
	Error  io.Writer

	name     string
	topFlags *flag.FlagSet
	commands map[string]Commander
}

// Register register a commander
func (c *CommandLine) Register(commander Commander) error {
	if _, ok := c.commands[commander.Name()]; ok {
		return ExistCommander
	}

	c.commands[commander.Name()] = commander
	return nil
}

// Name returns name of the command-line
func (c *CommandLine) Name() string {
	return c.name
}

// Execute execute command-line
func (c *CommandLine) Execute(ctx context.Context, args ...interface{}) ExitStatus {
	if c.topFlags.NArg() < 1 {
		c.topFlags.Usage()
		c.explain()
		return ExitUsageError
	}

	name := c.topFlags.Arg(0)
	if commander, ok := c.commands[name]; ok {
		f := flag.NewFlagSet(name, flag.ContinueOnError)
		commander.SetFlags(f)
		if f.Parse(c.topFlags.Args()[1:]) != nil {
			f.Usage()
			return ExitUsageError
		}

		status := commander.Execute(ctx, f, args...)
		if status == ExitUsageError {
			f.Usage()
		}
		return status
	}

	c.topFlags.Usage()
	return ExitUsageError
}

// explain explain command-line
func (c *CommandLine) explain() {
	_, _ = fmt.Fprintln(c.Error, "commands:")
	for name, commander := range c.commands {
		_, _ = fmt.Fprintf(c.Error, "\t%s\t\t: %s\n", name, commander.Synopsis())
	}
	_, _ = fmt.Fprintln(c.Error)
}

// New create a command line
func New(topFlags *flag.FlagSet, name string) *CommandLine {
	return &CommandLine{
		name:     name,
		topFlags: topFlags,
		commands: make(map[string]Commander),

		Output: os.Stdout,
		Error:  os.Stderr,
	}
}

// DefaultCommandLine is the default command-line
var DefaultCommandLine *CommandLine

func init() {
	DefaultCommandLine = New(flag.CommandLine, path.Base(os.Args[0]))
}

// Register register a commander into DefaultCommandLine
func Register(commander Commander) error {
	return DefaultCommandLine.Register(commander)
}

// Execute execute command-line
func Execute(ctx context.Context, args ...interface{}) ExitStatus {
	return DefaultCommandLine.Execute(ctx, args...)
}
