package subcommand

import (
	"os"
	"runtime"
	"text/template"

	"github.com/spf13/cobra"
)

const (
	versionTemplate = "Version: {{.Version}}\nGo Version: {{.GoVersion}}\nBuilt: {{.BuildTime}}(Rev: {{.GitRevision}})\n"
)

func Version(version, revision, buildTime string) *cobra.Command {
	return &cobra.Command{
		Use:              "version",
		Short:            "Print version info",
		PersistentPreRun: func(_ *cobra.Command, _ []string) {},
		Run: func(cmd *cobra.Command, args []string) {
			tpl, _ := template.New("version").Parse(versionTemplate)
			_ = tpl.Execute(os.Stdout, map[string]string{
				"Version":     version,
				"GoVersion":   runtime.Version(),
				"BuildTime":   buildTime,
				"GitRevision": revision,
			})
		},
	}
}
