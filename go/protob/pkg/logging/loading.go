package logging

import (
	"time"

	"github.com/briandowns/spinner"
)

const (
	loadingCharacterIndex    = 14
	loadingAnimationDuration = 80 * time.Millisecond
)

type loading struct {
	bar *spinner.Spinner
}

func (l *loading) Start() {
	l.bar.Start()
}

func NewLoading() *loading {
	bar := spinner.New(spinner.CharSets[loadingCharacterIndex], loadingAnimationDuration)
	bar.Prefix = "  "

	return &loading{bar: bar}
}
