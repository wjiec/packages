package logging

import (
	"os"
	"time"

	"github.com/briandowns/spinner"
)

const (
	loadingCharacterIndex    = 14
	loadingAnimationDuration = 80 * time.Millisecond
)

var (
	bar           = spinner.New(spinner.CharSets[loadingCharacterIndex], loadingAnimationDuration)
	loadingLogger = NewLogger(os.Stdout)
)

func init() {
	bar.HideCursor = true
	bar.Prefix = " "
}

type Status struct{}

func (s *Status) LoadingText(text string) {
	bar.Suffix = " " + text
}

func (s *Status) Success(format string, args ...interface{}) {
	bar.Stop()
	loadingLogger.Success(format, args...)
}

func (s *Status) Fatal(format string, args ...interface{}) {
	bar.Stop()
	loadingLogger.Fatal(format, args...)
}

func Loading(text string, action func(*Status)) {
	bar.Suffix = " " + text
	bar.Start()
	action(&Status{})
	bar.Stop()
}
