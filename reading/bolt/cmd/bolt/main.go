package main

import (
	"bytes"
	"encoding/binary"
	"errors"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"math/rand"
	"os"
	"runtime"
	"runtime/pprof"
	"strconv"
	"strings"
	"time"
	"unicode"
	"unicode/utf8"
	"unsafe"

	"github.com/boltdb/bolt"
)

var (
	// ErrUsage is returned when a usage message was printed and the process
	// should simply exit with an error.
	ErrUsage = errors.New("usage")

	// ErrUnknownCommand is returned when a CLI command is not specified.
	ErrUnknownCommand = errors.New("unknown command")

	// ErrPathRequired is returned when the path to a Bolt database is not specified.
	ErrPathRequired = errors.New("path required")

	// ErrFileNotFound is returned when a Bolt database does not exist.
	ErrFileNotFound = errors.New("file not found")

	// ErrInvalidValue is returned when a benchmark reads an unexpected value.
	ErrInvalidValue = errors.New("invalid value")

	// ErrCorrupt is returned when a checking a data file finds errors.
	ErrCorrupt = errors.New("invalid value")

	// ErrNonDivisibleBatchSize is returned when the batch size can't be evenly
	// divided by the iteration count.
	ErrNonDivisibleBatchSize = errors.New("number of iterations must be divisible by the batch size")

	// ErrPageIDRequired is returned when a required page id is not specified.
	ErrPageIDRequired = errors.New("page id required")

	// ErrPageNotFound is returned when specifying a page above the high water mark.
	ErrPageNotFound = errors.New("page not found")

	// ErrPageFreed is returned when reading a page that has already been freed.
	ErrPageFreed = errors.New("page freed")
)

// PageHeaderSize represents the size of the bolt.page header.
const PageHeaderSize = 16

func main() {
	m := NewMain()
	if err := m.Run(os.Args[1:]...); err == ErrUsage {
		os.Exit(2)
	} else if err != nil {
		fmt.Println(err.Error())
		os.Exit(1)
	}
}

// Main represents the main program execution.
type Main struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// NewMain returns a new instance of Main connect to the standard input/output.
func NewMain() *Main {
	return &Main{
		Stdin:  os.Stdin,
		Stdout: os.Stdout,
		Stderr: os.Stderr,
	}
}

// Run executes the program.
func (m *Main) Run(args ...string) error {
	// 必须指定需要执行的子命令
	// Require a command at the beginning.
	if len(args) == 0 || strings.HasPrefix(args[0], "-") {
		_, _ = fmt.Fprintln(m.Stderr, m.Usage())
		return ErrUsage
	}

	// Execute command.
	switch args[0] {
	case "help":
		// 打印帮助信息
		_, _ = fmt.Fprintln(m.Stderr, m.Usage())
		return ErrUsage
	case "bench":
		// 执行性能测试
		return newBenchCommand(m).Run(args[1:]...)
	case "check":
		return newCheckCommand(m).Run(args[1:]...)
	case "compact":
		return newCompactCommand(m).Run(args[1:]...)
	case "dump":
		return newDumpCommand(m).Run(args[1:]...)
	case "info":
		return newInfoCommand(m).Run(args[1:]...)
	case "page":
		return newPageCommand(m).Run(args[1:]...)
	case "pages":
		return newPagesCommand(m).Run(args[1:]...)
	case "stats":
		return newStatsCommand(m).Run(args[1:]...)
	default:
		return ErrUnknownCommand
	}
}

// Usage returns the help message.
func (m *Main) Usage() string {
	return strings.TrimLeft(`
Bolt is a tool for inspecting bolt databases.

Usage:

	bolt command [arguments]

The commands are:

    bench       run synthetic benchmark against bolt
    check       verifies integrity of bolt database
    compact     copies a bolt database, compacting it in the process
    info        print basic info
    help        print this screen
    pages       print list of pages with their types
    stats       iterate over all pages and generate usage stats

Use "bolt [command] -h" for more information about a command.
`, "\n")
}

// CheckCommand represents the "check" command execution.
type CheckCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// NewCheckCommand returns a CheckCommand.
func newCheckCommand(m *Main) *CheckCommand {
	return &CheckCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *CheckCommand) Run(args ...string) error {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	help := fs.Bool("h", false, "")
	if err := fs.Parse(args); err != nil {
		return err
	} else if *help {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	}

	// Require database path.
	path := fs.Arg(0)
	if path == "" {
		return ErrPathRequired
	} else if _, err := os.Stat(path); os.IsNotExist(err) {
		return ErrFileNotFound
	}

	// Open database.
	db, err := bolt.Open(path, 0666, nil)
	if err != nil {
		return err
	}
	defer db.Close()

	// Perform consistency check.
	return db.View(func(tx *bolt.Tx) error {
		var count int
		ch := tx.Check()
	loop:
		for {
			select {
			case err, ok := <-ch:
				if !ok {
					break loop
				}
				fmt.Fprintln(cmd.Stdout, err)
				count++
			}
		}

		// Print summary of errors.
		if count > 0 {
			fmt.Fprintf(cmd.Stdout, "%d errors found\n", count)
			return ErrCorrupt
		}

		// Notify user that database is valid.
		fmt.Fprintln(cmd.Stdout, "OK")
		return nil
	})
}

// Usage returns the help message.
func (cmd *CheckCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt check PATH

Check opens a database at PATH and runs an exhaustive check to verify that
all pages are accessible or are marked as freed. It also verifies that no
pages are double referenced.

Verification errors will stream out as they are found and the process will
return after all pages have been checked.
`, "\n")
}

// InfoCommand represents the "info" command execution.
type InfoCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// NewInfoCommand returns a InfoCommand.
func newInfoCommand(m *Main) *InfoCommand {
	return &InfoCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *InfoCommand) Run(args ...string) error {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	help := fs.Bool("h", false, "")
	if err := fs.Parse(args); err != nil {
		return err
	} else if *help {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	}

	// Require database path.
	path := fs.Arg(0)
	if path == "" {
		return ErrPathRequired
	} else if _, err := os.Stat(path); os.IsNotExist(err) {
		return ErrFileNotFound
	}

	// Open the database.
	db, err := bolt.Open(path, 0666, nil)
	if err != nil {
		return err
	}
	defer db.Close()

	// Print basic database info.
	info := db.Info()
	fmt.Fprintf(cmd.Stdout, "Page Size: %d\n", info.PageSize)

	return nil
}

// Usage returns the help message.
func (cmd *InfoCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt info PATH

Info prints basic information about the Bolt database at PATH.
`, "\n")
}

// DumpCommand represents the "dump" command execution.
type DumpCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// newDumpCommand returns a DumpCommand.
func newDumpCommand(m *Main) *DumpCommand {
	return &DumpCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *DumpCommand) Run(args ...string) error {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	help := fs.Bool("h", false, "")
	if err := fs.Parse(args); err != nil {
		return err
	} else if *help {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	}

	// Require database path and page id.
	path := fs.Arg(0)
	if path == "" {
		return ErrPathRequired
	} else if _, err := os.Stat(path); os.IsNotExist(err) {
		return ErrFileNotFound
	}

	// Read page ids.
	pageIDs, err := atois(fs.Args()[1:])
	if err != nil {
		return err
	} else if len(pageIDs) == 0 {
		return ErrPageIDRequired
	}

	// Open database to retrieve page size.
	pageSize, err := ReadPageSize(path)
	if err != nil {
		return err
	}

	// Open database file handler.
	f, err := os.Open(path)
	if err != nil {
		return err
	}
	defer func() { _ = f.Close() }()

	// Print each page listed.
	for i, pageID := range pageIDs {
		// Print a separator.
		if i > 0 {
			fmt.Fprintln(cmd.Stdout, "===============================================")
		}

		// Print page to stdout.
		if err := cmd.PrintPage(cmd.Stdout, f, pageID, pageSize); err != nil {
			return err
		}
	}

	return nil
}

// PrintPage prints a given page as hexadecimal.
func (cmd *DumpCommand) PrintPage(w io.Writer, r io.ReaderAt, pageID int, pageSize int) error {
	const bytesPerLineN = 16

	// Read page into buffer.
	buf := make([]byte, pageSize)
	addr := pageID * pageSize
	if n, err := r.ReadAt(buf, int64(addr)); err != nil {
		return err
	} else if n != pageSize {
		return io.ErrUnexpectedEOF
	}

	// Write out to writer in 16-byte lines.
	var prev []byte
	var skipped bool
	for offset := 0; offset < pageSize; offset += bytesPerLineN {
		// Retrieve current 16-byte line.
		line := buf[offset : offset+bytesPerLineN]
		isLastLine := (offset == (pageSize - bytesPerLineN))

		// If it's the same as the previous line then print a skip.
		if bytes.Equal(line, prev) && !isLastLine {
			if !skipped {
				fmt.Fprintf(w, "%07x *\n", addr+offset)
				skipped = true
			}
		} else {
			// Print line as hexadecimal in 2-byte groups.
			fmt.Fprintf(w, "%07x %04x %04x %04x %04x %04x %04x %04x %04x\n", addr+offset,
				line[0:2], line[2:4], line[4:6], line[6:8],
				line[8:10], line[10:12], line[12:14], line[14:16],
			)

			skipped = false
		}

		// Save the previous line.
		prev = line
	}
	fmt.Fprint(w, "\n")

	return nil
}

// Usage returns the help message.
func (cmd *DumpCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt dump -page PAGEID PATH

Dump prints a hexadecimal dump of a single page.
`, "\n")
}

// PageCommand represents the "page" command execution.
type PageCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// newPageCommand returns a PageCommand.
func newPageCommand(m *Main) *PageCommand {
	return &PageCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *PageCommand) Run(args ...string) error {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	help := fs.Bool("h", false, "")
	if err := fs.Parse(args); err != nil {
		return err
	} else if *help {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	}

	// Require database path and page id.
	path := fs.Arg(0)
	if path == "" {
		return ErrPathRequired
	} else if _, err := os.Stat(path); os.IsNotExist(err) {
		return ErrFileNotFound
	}

	// Read page ids.
	pageIDs, err := atois(fs.Args()[1:])
	if err != nil {
		return err
	} else if len(pageIDs) == 0 {
		return ErrPageIDRequired
	}

	// Open database file handler.
	f, err := os.Open(path)
	if err != nil {
		return err
	}
	defer func() { _ = f.Close() }()

	// Print each page listed.
	for i, pageID := range pageIDs {
		// Print a separator.
		if i > 0 {
			fmt.Fprintln(cmd.Stdout, "===============================================")
		}

		// Retrieve page info and page size.
		p, buf, err := ReadPage(path, pageID)
		if err != nil {
			return err
		}

		// Print basic page info.
		fmt.Fprintf(cmd.Stdout, "Page ID:    %d\n", p.id)
		fmt.Fprintf(cmd.Stdout, "Page Type:  %s\n", p.Type())
		fmt.Fprintf(cmd.Stdout, "Total Size: %d bytes\n", len(buf))

		// Print type-specific data.
		switch p.Type() {
		case "meta":
			err = cmd.PrintMeta(cmd.Stdout, buf)
		case "leaf":
			err = cmd.PrintLeaf(cmd.Stdout, buf)
		case "branch":
			err = cmd.PrintBranch(cmd.Stdout, buf)
		case "freelist":
			err = cmd.PrintFreelist(cmd.Stdout, buf)
		}
		if err != nil {
			return err
		}
	}

	return nil
}

// PrintMeta prints the data from the meta page.
func (cmd *PageCommand) PrintMeta(w io.Writer, buf []byte) error {
	m := (*meta)(unsafe.Pointer(&buf[PageHeaderSize]))
	fmt.Fprintf(w, "Version:    %d\n", m.version)
	fmt.Fprintf(w, "Page Size:  %d bytes\n", m.pageSize)
	fmt.Fprintf(w, "Flags:      %08x\n", m.flags)
	fmt.Fprintf(w, "Root:       <pgid=%d>\n", m.root.root)
	fmt.Fprintf(w, "Freelist:   <pgid=%d>\n", m.freelist)
	fmt.Fprintf(w, "HWM:        <pgid=%d>\n", m.pgid)
	fmt.Fprintf(w, "Txn ID:     %d\n", m.txid)
	fmt.Fprintf(w, "Checksum:   %016x\n", m.checksum)
	fmt.Fprintf(w, "\n")
	return nil
}

// PrintLeaf prints the data for a leaf page.
func (cmd *PageCommand) PrintLeaf(w io.Writer, buf []byte) error {
	p := (*page)(unsafe.Pointer(&buf[0]))

	// Print number of items.
	fmt.Fprintf(w, "Item Count: %d\n", p.count)
	fmt.Fprintf(w, "\n")

	// Print each key/value.
	for i := uint16(0); i < p.count; i++ {
		e := p.leafPageElement(i)

		// Format key as string.
		var k string
		if isPrintable(string(e.key())) {
			k = fmt.Sprintf("%q", string(e.key()))
		} else {
			k = fmt.Sprintf("%x", string(e.key()))
		}

		// Format value as string.
		var v string
		if (e.flags & uint32(bucketLeafFlag)) != 0 {
			b := (*bucket)(unsafe.Pointer(&e.value()[0]))
			v = fmt.Sprintf("<pgid=%d,seq=%d>", b.root, b.sequence)
		} else if isPrintable(string(e.value())) {
			v = fmt.Sprintf("%q", string(e.value()))
		} else {
			v = fmt.Sprintf("%x", string(e.value()))
		}

		fmt.Fprintf(w, "%s: %s\n", k, v)
	}
	fmt.Fprintf(w, "\n")
	return nil
}

// PrintBranch prints the data for a leaf page.
func (cmd *PageCommand) PrintBranch(w io.Writer, buf []byte) error {
	p := (*page)(unsafe.Pointer(&buf[0]))

	// Print number of items.
	fmt.Fprintf(w, "Item Count: %d\n", p.count)
	fmt.Fprintf(w, "\n")

	// Print each key/value.
	for i := uint16(0); i < p.count; i++ {
		e := p.branchPageElement(i)

		// Format key as string.
		var k string
		if isPrintable(string(e.key())) {
			k = fmt.Sprintf("%q", string(e.key()))
		} else {
			k = fmt.Sprintf("%x", string(e.key()))
		}

		fmt.Fprintf(w, "%s: <pgid=%d>\n", k, e.pgid)
	}
	fmt.Fprintf(w, "\n")
	return nil
}

// PrintFreelist prints the data for a freelist page.
func (cmd *PageCommand) PrintFreelist(w io.Writer, buf []byte) error {
	p := (*page)(unsafe.Pointer(&buf[0]))

	// Print number of items.
	fmt.Fprintf(w, "Item Count: %d\n", p.count)
	fmt.Fprintf(w, "\n")

	// Print each page in the freelist.
	ids := (*[maxAllocSize]pgid)(unsafe.Pointer(&p.ptr))
	for i := uint16(0); i < p.count; i++ {
		fmt.Fprintf(w, "%d\n", ids[i])
	}
	fmt.Fprintf(w, "\n")
	return nil
}

// PrintPage prints a given page as hexadecimal.
func (cmd *PageCommand) PrintPage(w io.Writer, r io.ReaderAt, pageID int, pageSize int) error {
	const bytesPerLineN = 16

	// Read page into buffer.
	buf := make([]byte, pageSize)
	addr := pageID * pageSize
	if n, err := r.ReadAt(buf, int64(addr)); err != nil {
		return err
	} else if n != pageSize {
		return io.ErrUnexpectedEOF
	}

	// Write out to writer in 16-byte lines.
	var prev []byte
	var skipped bool
	for offset := 0; offset < pageSize; offset += bytesPerLineN {
		// Retrieve current 16-byte line.
		line := buf[offset : offset+bytesPerLineN]
		isLastLine := (offset == (pageSize - bytesPerLineN))

		// If it's the same as the previous line then print a skip.
		if bytes.Equal(line, prev) && !isLastLine {
			if !skipped {
				fmt.Fprintf(w, "%07x *\n", addr+offset)
				skipped = true
			}
		} else {
			// Print line as hexadecimal in 2-byte groups.
			fmt.Fprintf(w, "%07x %04x %04x %04x %04x %04x %04x %04x %04x\n", addr+offset,
				line[0:2], line[2:4], line[4:6], line[6:8],
				line[8:10], line[10:12], line[12:14], line[14:16],
			)

			skipped = false
		}

		// Save the previous line.
		prev = line
	}
	fmt.Fprint(w, "\n")

	return nil
}

// Usage returns the help message.
func (cmd *PageCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt page -page PATH pageid [pageid...]

Page prints one or more pages in human readable format.
`, "\n")
}

// PagesCommand represents the "pages" command execution.
type PagesCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// NewPagesCommand returns a PagesCommand.
func newPagesCommand(m *Main) *PagesCommand {
	return &PagesCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *PagesCommand) Run(args ...string) error {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	help := fs.Bool("h", false, "")
	if err := fs.Parse(args); err != nil {
		return err
	} else if *help {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	}

	// Require database path.
	path := fs.Arg(0)
	if path == "" {
		return ErrPathRequired
	} else if _, err := os.Stat(path); os.IsNotExist(err) {
		return ErrFileNotFound
	}

	// Open database.
	db, err := bolt.Open(path, 0666, nil)
	if err != nil {
		return err
	}
	defer func() { _ = db.Close() }()

	// Write header.
	fmt.Fprintln(cmd.Stdout, "ID       TYPE       ITEMS  OVRFLW")
	fmt.Fprintln(cmd.Stdout, "======== ========== ====== ======")

	return db.Update(func(tx *bolt.Tx) error {
		var id int
		for {
			p, err := tx.Page(id)
			if err != nil {
				return &PageError{ID: id, Err: err}
			} else if p == nil {
				break
			}

			// Only display count and overflow if this is a non-free page.
			var count, overflow string
			if p.Type != "free" {
				count = strconv.Itoa(p.Count)
				if p.OverflowCount > 0 {
					overflow = strconv.Itoa(p.OverflowCount)
				}
			}

			// Print table row.
			fmt.Fprintf(cmd.Stdout, "%-8d %-10s %-6s %-6s\n", p.ID, p.Type, count, overflow)

			// Move to the next non-overflow page.
			id += 1
			if p.Type != "free" {
				id += p.OverflowCount
			}
		}
		return nil
	})
}

// Usage returns the help message.
func (cmd *PagesCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt pages PATH

Pages prints a table of pages with their type (meta, leaf, branch, freelist).
Leaf and branch pages will show a key count in the "items" column while the
freelist will show the number of free pages in the "items" column.

The "overflow" column shows the number of blocks that the page spills over
into. Normally there is no overflow but large keys and values can cause
a single page to take up multiple blocks.
`, "\n")
}

// StatsCommand represents the "stats" command execution.
type StatsCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// NewStatsCommand returns a StatsCommand.
func newStatsCommand(m *Main) *StatsCommand {
	return &StatsCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *StatsCommand) Run(args ...string) error {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	help := fs.Bool("h", false, "")
	if err := fs.Parse(args); err != nil {
		return err
	} else if *help {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	}

	// Require database path.
	path, prefix := fs.Arg(0), fs.Arg(1)
	if path == "" {
		return ErrPathRequired
	} else if _, err := os.Stat(path); os.IsNotExist(err) {
		return ErrFileNotFound
	}

	// Open database.
	db, err := bolt.Open(path, 0666, nil)
	if err != nil {
		return err
	}
	defer db.Close()

	return db.View(func(tx *bolt.Tx) error {
		var s bolt.BucketStats
		var count int
		if err := tx.ForEach(func(name []byte, b *bolt.Bucket) error {
			if bytes.HasPrefix(name, []byte(prefix)) {
				s.Add(b.Stats())
				count += 1
			}
			return nil
		}); err != nil {
			return err
		}

		fmt.Fprintf(cmd.Stdout, "Aggregate statistics for %d buckets\n\n", count)

		fmt.Fprintln(cmd.Stdout, "Page count statistics")
		fmt.Fprintf(cmd.Stdout, "\tNumber of logical branch pages: %d\n", s.BranchPageN)
		fmt.Fprintf(cmd.Stdout, "\tNumber of physical branch overflow pages: %d\n", s.BranchOverflowN)
		fmt.Fprintf(cmd.Stdout, "\tNumber of logical leaf pages: %d\n", s.LeafPageN)
		fmt.Fprintf(cmd.Stdout, "\tNumber of physical leaf overflow pages: %d\n", s.LeafOverflowN)

		fmt.Fprintln(cmd.Stdout, "Tree statistics")
		fmt.Fprintf(cmd.Stdout, "\tNumber of keys/value pairs: %d\n", s.KeyN)
		fmt.Fprintf(cmd.Stdout, "\tNumber of levels in B+tree: %d\n", s.Depth)

		fmt.Fprintln(cmd.Stdout, "Page size utilization")
		fmt.Fprintf(cmd.Stdout, "\tBytes allocated for physical branch pages: %d\n", s.BranchAlloc)
		var percentage int
		if s.BranchAlloc != 0 {
			percentage = int(float32(s.BranchInuse) * 100.0 / float32(s.BranchAlloc))
		}
		fmt.Fprintf(cmd.Stdout, "\tBytes actually used for branch data: %d (%d%%)\n", s.BranchInuse, percentage)
		fmt.Fprintf(cmd.Stdout, "\tBytes allocated for physical leaf pages: %d\n", s.LeafAlloc)
		percentage = 0
		if s.LeafAlloc != 0 {
			percentage = int(float32(s.LeafInuse) * 100.0 / float32(s.LeafAlloc))
		}
		fmt.Fprintf(cmd.Stdout, "\tBytes actually used for leaf data: %d (%d%%)\n", s.LeafInuse, percentage)

		fmt.Fprintln(cmd.Stdout, "Bucket statistics")
		fmt.Fprintf(cmd.Stdout, "\tTotal number of buckets: %d\n", s.BucketN)
		percentage = 0
		if s.BucketN != 0 {
			percentage = int(float32(s.InlineBucketN) * 100.0 / float32(s.BucketN))
		}
		fmt.Fprintf(cmd.Stdout, "\tTotal number on inlined buckets: %d (%d%%)\n", s.InlineBucketN, percentage)
		percentage = 0
		if s.LeafInuse != 0 {
			percentage = int(float32(s.InlineBucketInuse) * 100.0 / float32(s.LeafInuse))
		}
		fmt.Fprintf(cmd.Stdout, "\tBytes used for inlined buckets: %d (%d%%)\n", s.InlineBucketInuse, percentage)

		return nil
	})
}

// Usage returns the help message.
func (cmd *StatsCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt stats PATH

Stats performs an extensive search of the database to track every page
reference. It starts at the current meta page and recursively iterates
through every accessible bucket.

The following errors can be reported:

    already freed
        The page is referenced more than once in the freelist.

    unreachable unfreed
        The page is not referenced by a bucket or in the freelist.

    reachable freed
        The page is referenced by a bucket but is also in the freelist.

    out of bounds
        A page is referenced that is above the high water mark.

    multiple references
        A page is referenced by more than one other page.

    invalid type
        The page type is not "meta", "leaf", "branch", or "freelist".

No errors should occur in your database. However, if for some reason you
experience corruption, please submit a ticket to the Bolt project page:

  https://github.com/boltdb/bolt/issues
`, "\n")
}

var benchBucketName = []byte("bench")

// 性能测试命令
// BenchCommand represents the "bench" command execution.
type BenchCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer
}

// NewBenchCommand returns a BenchCommand using the
func newBenchCommand(m *Main) *BenchCommand {
	return &BenchCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// 执行性能测试
// Run executes the "bench" command.
func (cmd *BenchCommand) Run(args ...string) error {
	// 配置并解析 bench 命令的参数
	// Parse CLI arguments.
	options, err := cmd.ParseFlags(args)
	if err != nil {
		return err
	}

	// 如果参数 work 未设置, 则会在执行完成之后删除临时文件, 否则将会保留临时文件
	// Remove path if "-work" is not set. Otherwise keep path.
	if options.Work {
		_, _ = fmt.Fprintf(cmd.Stdout, "work: %s\n", options.Path)
	} else {
		// 执行结束之后删除文件(可能是随机文件)
		defer func() { _ = os.Remove(options.Path) }()
	}

	// 创建数据库文件
	// Create database.
	db, err := bolt.Open(options.Path, 0666, nil)
	if err != nil {
		return err
	}
	db.NoSync = options.NoSync
	defer func() { _ = db.Close() }()

	// Write to the database.
	var results BenchResults
	if err := cmd.runWrites(db, options, &results); err != nil {
		return fmt.Errorf("write: %v", err)
	}

	// Read from the database.
	if err := cmd.runReads(db, options, &results); err != nil {
		return fmt.Errorf("bench: read: %s", err)
	}

	// Print results.
	_, _ = fmt.Fprintf(os.Stderr, "# Write\t%v\t(%v/op)\t(%v op/sec)\n",
		results.WriteDuration, results.WriteOpDuration(), results.WriteOpsPerSecond())
	_, _ = fmt.Fprintf(os.Stderr, "# Read\t%v\t(%v/op)\t(%v op/sec)\n",
		results.ReadDuration, results.ReadOpDuration(), results.ReadOpsPerSecond())
	_, _ = fmt.Fprintln(os.Stderr, "")
	return nil
}

// 解析 bench 命令的参数
// ParseFlags parses the command line flags.
func (cmd *BenchCommand) ParseFlags(args []string) (*BenchOptions, error) {
	var options BenchOptions

	// Parse flagSet.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	fs.StringVar(&options.ProfileMode, "profile-mode", "rw", "what mode to turn profiling on")
	fs.StringVar(&options.WriteMode, "write-mode", "seq", "write mode")
	fs.StringVar(&options.ReadMode, "read-mode", "seq", "read mode")
	fs.IntVar(&options.Iterations, "count", 1000, "operation(write or read) count")
	fs.IntVar(&options.BatchSize, "batch-size", 0, "the number of the batch operation count")
	fs.IntVar(&options.KeySize, "key-size", 8, "the size of the key")
	fs.IntVar(&options.ValueSize, "value-size", 32, "the size of the value")
	fs.StringVar(&options.CPUProfile, "cpuprofile", "", "file path of cpu profile")
	fs.StringVar(&options.MemProfile, "memprofile", "", "file path of mem profile")
	fs.StringVar(&options.BlockProfile, "blockprofile", "", "file path if block profile")
	fs.Float64Var(&options.FillPercent, "fill-percent", bolt.DefaultFillPercent, "the threshold for filling nodes when they split")
	fs.BoolVar(&options.NoSync, "no-sync", false, "whether calls fsync() after each commit")
	fs.BoolVar(&options.Work, "work", false, "whether keep path when bench completed")
	fs.StringVar(&options.Path, "path", "", "bench file path, generate temp path when not passed in")
	fs.SetOutput(cmd.Stderr)
	if err := fs.Parse(args); err != nil {
		return nil, err
	}

	// 如果 BatchSize 参数没设置的话, 那就初始化为参数 Iterations 的值
	// 注意 BatchSize 的值需要能被 Iterations 参数整除
	// Set batch size to iteration size if not set.
	// Require that batch size can be evenly divided by the iteration count.
	if options.BatchSize == 0 {
		options.BatchSize = options.Iterations
	} else if options.Iterations%options.BatchSize != 0 {
		return nil, ErrNonDivisibleBatchSize
	}

	// 当测试文件路径未指定时, 创建一个临时文件后获取路径然后删掉这个文件
	// Generate temp path if one is not passed in.
	if options.Path == "" {
		f, err := ioutil.TempFile("", "bolt-bench-")
		if err != nil {
			return nil, fmt.Errorf("temp file: %s", err)
		}

		// 关闭临时文件, 然后删掉
		_ = f.Close()
		_ = os.Remove(f.Name())

		// 获取临时文件的路径
		options.Path = f.Name()
	}

	return &options, nil
}

// 写入数据库测试
// Writes to the database.
func (cmd *BenchCommand) runWrites(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	// Start profiling for writes.
	if options.ProfileMode == "rw" || options.ProfileMode == "w" {
		cmd.startProfiling(options)
	}

	t := time.Now()

	var err error
	switch options.WriteMode {
	case "seq":
		err = cmd.runWritesSequential(db, options, results)
	case "rnd":
		err = cmd.runWritesRandom(db, options, results)
	case "seq-nest":
		err = cmd.runWritesSequentialNested(db, options, results)
	case "rnd-nest":
		err = cmd.runWritesRandomNested(db, options, results)
	default:
		return fmt.Errorf("invalid write mode: %s", options.WriteMode)
	}

	// 保存写入的执行时间
	// Save time to write.
	results.WriteDuration = time.Since(t)

	// 如果只需要对写入执行 Profiling 则直接停止
	// Stop profiling for writes only.
	if options.ProfileMode == "w" {
		cmd.stopProfiling()
	}

	return err
}

// 顺序键写入
func (cmd *BenchCommand) runWritesSequential(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	var i = uint32(0)
	return cmd.runWritesWithSource(db, options, results, func() uint32 { i++; return i })
}

// 随机键写入
func (cmd *BenchCommand) runWritesRandom(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	return cmd.runWritesWithSource(db, options, results, func() uint32 { return r.Uint32() })
}

// 顺序键写入
func (cmd *BenchCommand) runWritesSequentialNested(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	var i = uint32(0)
	return cmd.runWritesNestedWithSource(db, options, results, func() uint32 { i++; return i })
}

// 随机键写入
func (cmd *BenchCommand) runWritesRandomNested(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	r := rand.New(rand.NewSource(time.Now().UnixNano()))
	return cmd.runWritesNestedWithSource(db, options, results, func() uint32 { return r.Uint32() })
}

// 执行普通写入测试
// 在一个Bucket中写入 Iterations 个键值对, 每次批量写入 BatchSize 个键值对
func (cmd *BenchCommand) runWritesWithSource(db *bolt.DB, options *BenchOptions, results *BenchResults, keySource func() uint32) error {
	results.WriteOps = options.Iterations

	// 累计执行 Iterations 次, 每次批量执行 BatchSize 次
	for i := 0; i < options.Iterations; i += options.BatchSize {
		if err := db.Update(func(tx *bolt.Tx) error {
			// 创建默认的 Bucket(bench)
			b, _ := tx.CreateBucketIfNotExists(benchBucketName)
			// Bucket 文件的分割比例
			b.FillPercent = options.FillPercent

			for j := 0; j < options.BatchSize; j++ {
				key := make([]byte, options.KeySize)
				value := make([]byte, options.ValueSize)

				// 这里体现的是在写入模式上*键*是随机的还是顺序的(hash上的困难性?)
				// Write key as uint32.
				binary.BigEndian.PutUint32(key, keySource())

				// Insert key/value.
				if err := b.Put(key, value); err != nil {
					return err
				}
			}

			return nil
		}); err != nil {
			return err
		}
	}
	return nil
}

// 执行嵌套的的写入测试
// 这里的嵌套指的是在一个 Bucket 中嵌套一个 Bucket, 累计创建 Iterations/BatchSize 个嵌套 Bucket
// 嵌套的每个 Bucket 中包含 BatchSize 个键值对
func (cmd *BenchCommand) runWritesNestedWithSource(db *bolt.DB, options *BenchOptions, results *BenchResults, keySource func() uint32) error {
	results.WriteOps = options.Iterations

	for i := 0; i < options.Iterations; i += options.BatchSize {
		if err := db.Update(func(tx *bolt.Tx) error {
			top, err := tx.CreateBucketIfNotExists(benchBucketName)
			if err != nil {
				return err
			}
			top.FillPercent = options.FillPercent

			// Create bucket key.
			name := make([]byte, options.KeySize)
			binary.BigEndian.PutUint32(name, keySource())

			// Create bucket.
			b, err := top.CreateBucketIfNotExists(name)
			if err != nil {
				return err
			}
			b.FillPercent = options.FillPercent

			for j := 0; j < options.BatchSize; j++ {
				var key = make([]byte, options.KeySize)
				var value = make([]byte, options.ValueSize)

				// Generate key as uint32.
				binary.BigEndian.PutUint32(key, keySource())

				// Insert value into subbucket.
				if err := b.Put(key, value); err != nil {
					return err
				}
			}

			return nil
		}); err != nil {
			return err
		}
	}
	return nil
}

// Reads from the database.
func (cmd *BenchCommand) runReads(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	// 因为先执行的写入测试, 在写入是 rw 模式已经开启了 Profiling , 所以这里只需要检查 r 模式
	// Start profiling for reads.
	if options.ProfileMode == "r" {
		cmd.startProfiling(options)
	}

	t := time.Now()

	var err error
	switch options.ReadMode {
	case "seq":
		switch options.WriteMode {
		case "seq-nest", "rnd-nest":
			err = cmd.runReadsSequentialNested(db, options, results)
		default:
			err = cmd.runReadsSequential(db, options, results)
		}
	default:
		return fmt.Errorf("invalid read mode: %s", options.ReadMode)
	}

	// Save read time.
	results.ReadDuration = time.Since(t)

	// 停止收集数据
	// Stop profiling for reads.
	if options.ProfileMode == "rw" || options.ProfileMode == "r" {
		cmd.stopProfiling()
	}

	return err
}

// 进行普通模式下的读取测试, 连续读取完整的数据 1s 以上
func (cmd *BenchCommand) runReadsSequential(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	return db.View(func(tx *bolt.Tx) error {
		t := time.Now()

		for {
			var count int

			c := tx.Bucket(benchBucketName).Cursor()
			for k, v := c.First(); k != nil; k, v = c.Next() {
				if v == nil {
					return errors.New("invalid value")
				}
				count++
			}

			if options.WriteMode == "seq" && count != options.Iterations {
				return fmt.Errorf("read seq: iter mismatch: expected %d, got %d", options.Iterations, count)
			}

			results.ReadOps += count

			// 保证执行读取时间超过 1s
			// Make sure we do this for at least a second.
			if time.Since(t) >= time.Second {
				break
			}
		}

		return nil
	})
}

// 执行嵌套 Bucket 的读取测试, 遍历所有的内嵌 Bucket 的所有键值对, 连续执行 1s 以上
func (cmd *BenchCommand) runReadsSequentialNested(db *bolt.DB, options *BenchOptions, results *BenchResults) error {
	return db.View(func(tx *bolt.Tx) error {
		t := time.Now()

		for {
			var count int
			var top = tx.Bucket(benchBucketName)
			if err := top.ForEach(func(name, _ []byte) error {
				c := top.Bucket(name).Cursor()
				for k, v := c.First(); k != nil; k, v = c.Next() {
					if v == nil {
						return ErrInvalidValue
					}
					count++
				}
				return nil
			}); err != nil {
				return err
			}

			if options.WriteMode == "seq-nest" && count != options.Iterations {
				return fmt.Errorf("read seq-nest: iter mismatch: expected %d, got %d", options.Iterations, count)
			}

			results.ReadOps += count

			// Make sure we do this for at least a second.
			if time.Since(t) >= time.Second {
				break
			}
		}

		return nil
	})
}

// File handlers for the various profiles.
var cpuprofile, memprofile, blockprofile *os.File

// 根据配置启动参数开启相应的 Profiling
// Starts all profiles set on the options.
func (cmd *BenchCommand) startProfiling(options *BenchOptions) {
	var err error

	// 如果在参数中指定了 CpuProfile 保存的文件名, 则开启 Profiling
	// Start CPU profiling.
	if options.CPUProfile != "" {
		cpuprofile, err = os.Create(options.CPUProfile)
		if err != nil {
			_, _ = fmt.Fprintf(cmd.Stderr, "bench: could not create cpu profile %q: %v\n", options.CPUProfile, err)
			os.Exit(1)
		}
		_ = pprof.StartCPUProfile(cpuprofile)
	}

	// 如果在参数中指定了 MemProfile 保存的文件名, 则修改 MemProfileRate 值
	// Start memory profiling.
	if options.MemProfile != "" {
		memprofile, err = os.Create(options.MemProfile)
		if err != nil {
			_, _ = fmt.Fprintf(cmd.Stderr, "bench: could not create memory profile %q: %v\n", options.MemProfile, err)
			os.Exit(1)
		}

		runtime.MemProfileRate = 4096
	}

	// 如果在参数中指定了 BlockProfile 保存的文件名, 则修改 BlockProfileRate 的值
	// Start fatal profiling.
	if options.BlockProfile != "" {
		blockprofile, err = os.Create(options.BlockProfile)
		if err != nil {
			_, _ = fmt.Fprintf(cmd.Stderr, "bench: could not create block profile %q: %v\n", options.BlockProfile, err)
			os.Exit(1)
		}
		runtime.SetBlockProfileRate(1)
	}
}

// 停止所有的 Profiling
// Stops all profiles.
func (cmd *BenchCommand) stopProfiling() {
	if cpuprofile != nil {
		pprof.StopCPUProfile()
		_ = cpuprofile.Close()
		cpuprofile = nil
	}

	// 写入堆的信息 (a sampling of memory allocations of live objects)
	if memprofile != nil {
		_ = pprof.Lookup("heap").WriteTo(memprofile, 0)
		_ = memprofile.Close()
		memprofile = nil
	}

	// 写入 同步阻塞时的堆栈信息 (stack traces that led to blocking on synchronization primitives)
	if blockprofile != nil {
		_ = pprof.Lookup("block").WriteTo(blockprofile, 0)
		_ = blockprofile.Close()
		blockprofile = nil
		runtime.SetBlockProfileRate(0)
	}
}

// BenchOptions represents the set of options that can be passed to "bolt bench".
type BenchOptions struct {
	// 在哪些场景下需要开启 Profiling 收集CPU, Mem, Block的动态信息
	// r - 表示在读取的时候开启
	// w - 表示在写入的时候开启
	// rw - 表示在读取和写入时都开启 (默认值)
	ProfileMode   string

	// 写入模式
	// seq - 顺序写入 (默认值)
	// rnd - 随机写入
	// seq-nest - 嵌套 Bucket 的顺序写入
	// rnd-nest - 嵌套 Bucket 的随机写入
	WriteMode     string

	// 读取模式
	// req - 顺序读取 (默认值)
	ReadMode      string

	// 操作执行次数, 表示为 Write/Read 的总键值对个数
	// 默认值为 1000
	Iterations    int

	// 表示为每次批量执行的键值对个数
	// 分为两种情况去看, 在非 nested 测试下, 表示每次批量执行操作的键值对个数,
	// 在 nested 测试下表示为嵌套 Bucket 中的键值对数量
	// 默认值为 0 (会被覆盖为与 Iterations 值相同)
	BatchSize     int

	// 键的长度
	// 默认值为 8
	KeySize       int

	// 值的长度
	// 默认值为 32
	ValueSize     int

	// Cpu Profile 文件名
	CPUProfile    string

	// 堆申请数据信息文件名
	MemProfile    string

	// 在同步上阻塞的堆栈信息的保存文件名
	BlockProfile  string

	// 暂时无用
	StatsInterval time.Duration

	// Bucket 文件填充的比例, 超过该数值将会发生文件分割
	// 默认值为0.5
	FillPercent   float64

	// 是否在每次 commit 之后执行 fsync() 方法刷数据到文件系统
	NoSync        bool

	// 是否在执行结束之后删除数据库文件
	Work          bool

	// 数据库文件的路径
	Path          string
}

// 基准新能测试的结果
// BenchResults represents the performance results of the benchmark.
type BenchResults struct {
	// 写入次数
	WriteOps      int

	// 写入所花的时间
	WriteDuration time.Duration

	// 读取次数
	ReadOps       int

	// 读取所花的时间
	ReadDuration  time.Duration
}

// 返回平均每次写入所需要的时间
// Returns the duration for a single write operation.
func (r *BenchResults) WriteOpDuration() time.Duration {
	if r.WriteOps == 0 {
		return 0
	}
	return r.WriteDuration / time.Duration(r.WriteOps)
}

// 返回每秒可执行的写入次数
// Returns average number of write operations that can be performed per second.
func (r *BenchResults) WriteOpsPerSecond() int {
	var op = r.WriteOpDuration()
	if op == 0 {
		return 0
	}
	return int(time.Second) / int(op)
}

// 返回每次读取所需的时间
// Returns the duration for a single read operation.
func (r *BenchResults) ReadOpDuration() time.Duration {
	if r.ReadOps == 0 {
		return 0
	}
	return r.ReadDuration / time.Duration(r.ReadOps)
}

// 返回每秒能进行多少次读取次数
// Returns average number of read operations that can be performed per second.
func (r *BenchResults) ReadOpsPerSecond() int {
	var op = r.ReadOpDuration()
	if op == 0 {
		return 0
	}
	return int(time.Second) / int(op)
}

type PageError struct {
	ID  int
	Err error
}

func (e *PageError) Error() string {
	return fmt.Sprintf("page error: id=%d, err=%s", e.ID, e.Err)
}

// isPrintable returns true if the string is valid unicode and contains only printable runes.
func isPrintable(s string) bool {
	if !utf8.ValidString(s) {
		return false
	}
	for _, ch := range s {
		if !unicode.IsPrint(ch) {
			return false
		}
	}
	return true
}

// ReadPage reads page info & full page data from a path.
// This is not transactionally safe.
func ReadPage(path string, pageID int) (*page, []byte, error) {
	// Find page size.
	pageSize, err := ReadPageSize(path)
	if err != nil {
		return nil, nil, fmt.Errorf("read page size: %s", err)
	}

	// Open database file.
	f, err := os.Open(path)
	if err != nil {
		return nil, nil, err
	}
	defer f.Close()

	// Read one block into buffer.
	buf := make([]byte, pageSize)
	if n, err := f.ReadAt(buf, int64(pageID*pageSize)); err != nil {
		return nil, nil, err
	} else if n != len(buf) {
		return nil, nil, io.ErrUnexpectedEOF
	}

	// Determine total number of blocks.
	p := (*page)(unsafe.Pointer(&buf[0]))
	overflowN := p.overflow

	// Re-read entire page (with overflow) into buffer.
	buf = make([]byte, (int(overflowN)+1)*pageSize)
	if n, err := f.ReadAt(buf, int64(pageID*pageSize)); err != nil {
		return nil, nil, err
	} else if n != len(buf) {
		return nil, nil, io.ErrUnexpectedEOF
	}
	p = (*page)(unsafe.Pointer(&buf[0]))

	return p, buf, nil
}

// ReadPageSize reads page size a path.
// This is not transactionally safe.
func ReadPageSize(path string) (int, error) {
	// Open database file.
	f, err := os.Open(path)
	if err != nil {
		return 0, err
	}
	defer f.Close()

	// Read 4KB chunk.
	buf := make([]byte, 4096)
	if _, err := io.ReadFull(f, buf); err != nil {
		return 0, err
	}

	// Read page size from metadata.
	m := (*meta)(unsafe.Pointer(&buf[PageHeaderSize]))
	return int(m.pageSize), nil
}

// atois parses a slice of strings into integers.
func atois(strs []string) ([]int, error) {
	var a []int
	for _, str := range strs {
		i, err := strconv.Atoi(str)
		if err != nil {
			return nil, err
		}
		a = append(a, i)
	}
	return a, nil
}

// DO NOT EDIT. Copied from the "bolt" package.
const maxAllocSize = 0xFFFFFFF

// DO NOT EDIT. Copied from the "bolt" package.
const (
	branchPageFlag   = 0x01
	leafPageFlag     = 0x02
	metaPageFlag     = 0x04
	freelistPageFlag = 0x10
)

// DO NOT EDIT. Copied from the "bolt" package.
const bucketLeafFlag = 0x01

// DO NOT EDIT. Copied from the "bolt" package.
type pgid uint64

// DO NOT EDIT. Copied from the "bolt" package.
type txid uint64

// DO NOT EDIT. Copied from the "bolt" package.
type meta struct {
	magic    uint32
	version  uint32
	pageSize uint32
	flags    uint32
	root     bucket
	freelist pgid
	pgid     pgid
	txid     txid
	checksum uint64
}

// DO NOT EDIT. Copied from the "bolt" package.
type bucket struct {
	root     pgid
	sequence uint64
}

// DO NOT EDIT. Copied from the "bolt" package.
type page struct {
	id       pgid
	flags    uint16
	count    uint16
	overflow uint32
	ptr      uintptr
}

// DO NOT EDIT. Copied from the "bolt" package.
func (p *page) Type() string {
	if (p.flags & branchPageFlag) != 0 {
		return "branch"
	} else if (p.flags & leafPageFlag) != 0 {
		return "leaf"
	} else if (p.flags & metaPageFlag) != 0 {
		return "meta"
	} else if (p.flags & freelistPageFlag) != 0 {
		return "freelist"
	}
	return fmt.Sprintf("unknown<%02x>", p.flags)
}

// DO NOT EDIT. Copied from the "bolt" package.
func (p *page) leafPageElement(index uint16) *leafPageElement {
	n := &((*[0x7FFFFFF]leafPageElement)(unsafe.Pointer(&p.ptr)))[index]
	return n
}

// DO NOT EDIT. Copied from the "bolt" package.
func (p *page) branchPageElement(index uint16) *branchPageElement {
	return &((*[0x7FFFFFF]branchPageElement)(unsafe.Pointer(&p.ptr)))[index]
}

// DO NOT EDIT. Copied from the "bolt" package.
type branchPageElement struct {
	pos   uint32
	ksize uint32
	pgid  pgid
}

// DO NOT EDIT. Copied from the "bolt" package.
func (n *branchPageElement) key() []byte {
	buf := (*[maxAllocSize]byte)(unsafe.Pointer(n))
	return buf[n.pos : n.pos+n.ksize]
}

// DO NOT EDIT. Copied from the "bolt" package.
type leafPageElement struct {
	flags uint32
	pos   uint32
	ksize uint32
	vsize uint32
}

// DO NOT EDIT. Copied from the "bolt" package.
func (n *leafPageElement) key() []byte {
	buf := (*[maxAllocSize]byte)(unsafe.Pointer(n))
	return buf[n.pos : n.pos+n.ksize]
}

// DO NOT EDIT. Copied from the "bolt" package.
func (n *leafPageElement) value() []byte {
	buf := (*[maxAllocSize]byte)(unsafe.Pointer(n))
	return buf[n.pos+n.ksize : n.pos+n.ksize+n.vsize]
}

// CompactCommand represents the "compact" command execution.
type CompactCommand struct {
	Stdin  io.Reader
	Stdout io.Writer
	Stderr io.Writer

	SrcPath   string
	DstPath   string
	TxMaxSize int64
}

// newCompactCommand returns a CompactCommand.
func newCompactCommand(m *Main) *CompactCommand {
	return &CompactCommand{
		Stdin:  m.Stdin,
		Stdout: m.Stdout,
		Stderr: m.Stderr,
	}
}

// Run executes the command.
func (cmd *CompactCommand) Run(args ...string) (err error) {
	// Parse flags.
	fs := flag.NewFlagSet("", flag.ContinueOnError)
	fs.SetOutput(ioutil.Discard)
	fs.StringVar(&cmd.DstPath, "o", "", "")
	fs.Int64Var(&cmd.TxMaxSize, "tx-max-size", 65536, "")
	if err := fs.Parse(args); err == flag.ErrHelp {
		fmt.Fprintln(cmd.Stderr, cmd.Usage())
		return ErrUsage
	} else if err != nil {
		return err
	} else if cmd.DstPath == "" {
		return fmt.Errorf("output file required")
	}

	// Require database paths.
	cmd.SrcPath = fs.Arg(0)
	if cmd.SrcPath == "" {
		return ErrPathRequired
	}

	// Ensure source file exists.
	fi, err := os.Stat(cmd.SrcPath)
	if os.IsNotExist(err) {
		return ErrFileNotFound
	} else if err != nil {
		return err
	}
	initialSize := fi.Size()

	// Open source database.
	src, err := bolt.Open(cmd.SrcPath, 0444, nil)
	if err != nil {
		return err
	}
	defer src.Close()

	// Open destination database.
	dst, err := bolt.Open(cmd.DstPath, fi.Mode(), nil)
	if err != nil {
		return err
	}
	defer dst.Close()

	// Run compaction.
	if err := cmd.compact(dst, src); err != nil {
		return err
	}

	// Report stats on new size.
	fi, err = os.Stat(cmd.DstPath)
	if err != nil {
		return err
	} else if fi.Size() == 0 {
		return fmt.Errorf("zero db size")
	}
	fmt.Fprintf(cmd.Stdout, "%d -> %d bytes (gain=%.2fx)\n", initialSize, fi.Size(), float64(initialSize)/float64(fi.Size()))

	return nil
}

func (cmd *CompactCommand) compact(dst, src *bolt.DB) error {
	// commit regularly, or we'll run out of memory for large datasets if using one transaction.
	var size int64
	tx, err := dst.Begin(true)
	if err != nil {
		return err
	}
	defer tx.Rollback()

	if err := cmd.walk(src, func(keys [][]byte, k, v []byte, seq uint64) error {
		// On each key/value, check if we have exceeded tx size.
		sz := int64(len(k) + len(v))
		if size+sz > cmd.TxMaxSize && cmd.TxMaxSize != 0 {
			// Commit previous transaction.
			if err := tx.Commit(); err != nil {
				return err
			}

			// Start new transaction.
			tx, err = dst.Begin(true)
			if err != nil {
				return err
			}
			size = 0
		}
		size += sz

		// Create bucket on the root transaction if this is the first level.
		nk := len(keys)
		if nk == 0 {
			bkt, err := tx.CreateBucket(k)
			if err != nil {
				return err
			}
			if err := bkt.SetSequence(seq); err != nil {
				return err
			}
			return nil
		}

		// Create buckets on subsequent levels, if necessary.
		b := tx.Bucket(keys[0])
		if nk > 1 {
			for _, k := range keys[1:] {
				b = b.Bucket(k)
			}
		}

		// If there is no value then this is a bucket call.
		if v == nil {
			bkt, err := b.CreateBucket(k)
			if err != nil {
				return err
			}
			if err := bkt.SetSequence(seq); err != nil {
				return err
			}
			return nil
		}

		// Otherwise treat it as a key/value pair.
		return b.Put(k, v)
	}); err != nil {
		return err
	}

	return tx.Commit()
}

// walkFunc is the type of the function called for keys (buckets and "normal"
// values) discovered by Walk. keys is the list of keys to descend to the bucket
// owning the discovered key/value pair k/v.
type walkFunc func(keys [][]byte, k, v []byte, seq uint64) error

// walk walks recursively the bolt database db, calling walkFn for each key it finds.
func (cmd *CompactCommand) walk(db *bolt.DB, walkFn walkFunc) error {
	return db.View(func(tx *bolt.Tx) error {
		return tx.ForEach(func(name []byte, b *bolt.Bucket) error {
			return cmd.walkBucket(b, nil, name, nil, b.Sequence(), walkFn)
		})
	})
}

func (cmd *CompactCommand) walkBucket(b *bolt.Bucket, keypath [][]byte, k, v []byte, seq uint64, fn walkFunc) error {
	// Execute callback.
	if err := fn(keypath, k, v, seq); err != nil {
		return err
	}

	// If this is not a bucket then stop.
	if v != nil {
		return nil
	}

	// Iterate over each child key/value.
	keypath = append(keypath, k)
	return b.ForEach(func(k, v []byte) error {
		if v == nil {
			bkt := b.Bucket(k)
			return cmd.walkBucket(bkt, keypath, k, nil, bkt.Sequence(), fn)
		}
		return cmd.walkBucket(b, keypath, k, v, b.Sequence(), fn)
	})
}

// Usage returns the help message.
func (cmd *CompactCommand) Usage() string {
	return strings.TrimLeft(`
usage: bolt compact [options] -o DST SRC

Compact opens a database at SRC path and walks it recursively, copying keys
as they are found from all buckets, to a newly created database at DST path.

The original database is left untouched.

Additional options include:

	-tx-max-size NUM
		Specifies the maximum size of individual transactions.
		Defaults to 64KB.
`, "\n")
}
