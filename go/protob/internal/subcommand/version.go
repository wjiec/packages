package subcommand

import (
	"os"
	"protob/internal/home"
	"protob/pkg/logging"
	"runtime"
	"text/template"

	"github.com/spf13/cobra"
)

const versionTemplate = `CommandLine: protob
 Version: {{.Version}}
 Go Version: {{.GoVersion}}
 Built: {{.BuildTime}}(Rev: {{.GitRevision}})
 Base Dir: {{.BaseDir}}

Protobuf: protoc
{{- if .SysCompilerVersion }}
 System Compiler: {{.SysCompilerVersion}}(system)
{{- end }}
{{- if .EmbeddedCompilerVersion }}
 Embedded Compiler: {{.EmbeddedCompilerVersion}}(embedded)
{{- end }}
 Include Path: {{.IncludePath}}
`

func Version(version, revision, buildTime string) *cobra.Command {
	return &cobra.Command{
		Use:   "version",
		Short: "Print version info",
		Run: func(cmd *cobra.Command, args []string) {
			s := cmd.Context().Value("home").(*home.State)

			sysCompilerVersion, sysErr := s.SysCompilerVersion()
			embeddedCompilerVersion, embeddedErr := s.EmbeddedCompilerVersion()
			if sysErr != nil && embeddedErr != nil {
				logging.Fatal("%s(sys), %s(embedded)", sysErr, embeddedErr)
			}

			data := map[string]string{
				"Version":                 version,
				"GoVersion":               runtime.Version(),
				"BuildTime":               buildTime,
				"GitRevision":             revision,
				"BaseDir":                 home.BaseDir(),
				"SysCompilerVersion":      sysCompilerVersion,
				"EmbeddedCompilerVersion": embeddedCompilerVersion,
				"IncludePath":             s.DependencyPath,
			}

			tpl, _ := template.New("version").Parse(versionTemplate)
			_ = tpl.Execute(os.Stdout, data)
		},
	}
}
