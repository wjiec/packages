package crlf

import (
	"bulk/pkg/os/fs"
	"bulk/pkg/xcmd"
	"bytes"
	"io/ioutil"
	"os"
	"path/filepath"

	"github.com/pkg/errors"
	"github.com/spf13/cobra"
)

var flags struct {
	DryRun    bool `default:"false" usage:"just print a list of files that will be converted"`
	Recursive bool `shorthand:"r" default:"false" usage:"recursively convert all files in the directories"`
}

func Command() *cobra.Command {
	cmd := &cobra.Command{
		Use:   "crlf",
		Short: "Convert text files between dos and unix newline style",
		Args:  cobra.MinimumNArgs(1),
		RunE: func(cmd *cobra.Command, args []string) error {
			for _, arg := range args {
				if ok, err := fs.Dir(arg); err != nil {
					return errors.Wrap(err, "cannot stat file")
				} else if ok && !flags.Recursive {
					return errors.Errorf("cannot convert '%s': is a directory", arg)
				}
			}

			return convert(args)
		},
	}
	_ = xcmd.Bind(cmd, &flags)

	return cmd
}

func convert(filenames []string) error {
	for _, filename := range filenames {
		if ok, err := fs.Dir(filename); err != nil {
			return err
		} else if ok {
			err := filepath.Walk(filename, func(path string, info os.FileInfo, err error) error {
				if err != nil {
					return err
				}

				if !info.IsDir() {
					go convertFile(path)
				}
				return nil
			})
			if err != nil {
				return err
			}
		} else {
			go convertFile(filename)
		}
	}

	return nil
}

func convertFile(filename string) {
	body, _ := ioutil.ReadFile(filename)
	_ = ioutil.WriteFile(filename, bytes.Replace(body, []byte("\r\n"), []byte("\n"), -1), 0777)
}
