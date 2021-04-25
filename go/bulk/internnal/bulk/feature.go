package bulk

var flags struct {
	DryRun bool `default:"false" usage:"don’t actually do action, just show what's going to be done"`
}

func DryRun() bool {
	return flags.DryRun
}
