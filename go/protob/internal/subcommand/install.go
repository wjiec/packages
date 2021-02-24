package subcommand

import (
	"archive/zip"
	"bytes"
	"context"
	"errors"
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
	"net/url"
	"os"
	"os/exec"
	"path/filepath"
	"protob/internal/home"
	"protob/pkg/logging"
	"protob/pkg/utils"
	"runtime"
	"strings"

	"github.com/google/go-github/v33/github"
	"github.com/spf13/cobra"
)

var join = filepath.Join

func Install() *cobra.Command {
	cmd := &cobra.Command{
		Use:   "install",
		Short: "Install Protobuf compiler and google dependencies",
		PreRun: func(cmd *cobra.Command, args []string) {
			if proxy, err := cmd.PersistentFlags().GetString("proxy"); err == nil && proxy != "" {
				httpClient.Transport = &http.Transport{
					Proxy: func(_ *http.Request) (*url.URL, error) {
						return url.Parse(proxy)
					},
				}
			}
		},
		Run: func(cmd *cobra.Command, args []string) {
			skipCompiler, _ := cmd.PersistentFlags().GetBool("skip-protobuf")
			if !skipCompiler {
				logging.Loading("fetch latest release", func(status *logging.Status) {
					var err error
					var release *github.RepositoryRelease
					if release, err = latestRelease(cmd.Context(), compilerOwner, compilerRepo); err != nil {
						status.Fatal(err.Error())
						return
					}

					status.LoadingText(fmt.Sprintf("download latest release version %s", release.GetTagName()))
					content, err := downloadRelease(cmd.Context(), release)
					if err != nil {
						status.Fatal(err.Error())
					}

					status.LoadingText(fmt.Sprintf("extract assert into %s", home.BaseDir()))
					if err := extractCompilers(content, home.BaseDir()); err != nil {
						status.Fatal(err.Error())
						return
					}

					status.Success("protobuf installed")
				})
			}

			skipGoGoCompiler, _ := cmd.PersistentFlags().GetBool("skip-gogo-protobuf")
			if !skipGoGoCompiler {
				logging.Loading("fetch gogo-protobuf latest release", func(status *logging.Status) {
					if release, err := latestRelease(cmd.Context(), gogoOwner, gogoRepo); err != nil {
						status.Fatal(err.Error())
						return
					} else {
						status.LoadingText(fmt.Sprintf("download gogo-protobuf version %s", release.GetTagName()))
						content, err := downloadContent(cmd.Context(), release.GetZipballURL())
						if err != nil {
							status.Fatal(err.Error())
						}

						tempDir := join(home.BaseDir(), ".temp")
						status.LoadingText(fmt.Sprintf("extract gogo-protobuf into %s", tempDir))
						if err := extractGoGoProtobuf(content, tempDir); err != nil {
							status.Fatal(err.Error())
							return
						}

						if err := copyGoGoProtobufFiles(tempDir, home.ExpandDir("include")); err != nil {
							status.Fatal(err.Error())
							return
						}

						status.LoadingText("compile protoc-gen-gogofast")
						if err := compileCompilerGen(join(tempDir, gogoGenFast, "main.go"), home.ExpandExecutable(gogoGenFast)); err != nil {
							status.Fatal(err.Error())
							return
						}

						status.LoadingText("compile protoc-gen-gogofaster")
						if err := compileCompilerGen(join(tempDir, gogoGenFaster, "main.go"), home.ExpandExecutable(gogoGenFaster)); err != nil {
							status.Fatal(err.Error())
							return
						}

						status.LoadingText("compile protoc-gen-gogoslick")
						if err := compileCompilerGen(join(tempDir, gogoGenSlick, "main.go"), home.ExpandExecutable(gogoGenSlick)); err != nil {
							status.Fatal(err.Error())
							return
						}

						if err := os.RemoveAll(tempDir); err != nil {
							status.Fatal(err.Error())
							return
						}

						status.Success("gogo-protobuf installed")
					}
				})
			}
		},
	}

	cmd.PersistentFlags().String("proxy", "", "proxy for http request")
	cmd.PersistentFlags().Bool("skip-protobuf", false, "skip install protobuf")
	cmd.PersistentFlags().Bool("skip-gogo-protobuf", false, "skip install gogo-protobuf")

	return cmd
}

var (
	httpClient        = &http.Client{}
	githubClient      = github.NewClient(httpClient)
	list1Options      = &github.ListOptions{PerPage: 1}
	errAssertNotFound = errors.New("install: unable to matched assert")
)

const (
	compilerOwner = "protocolbuffers"
	compilerRepo  = "protobuf"
	gogoOwner     = "gogo"
	gogoRepo      = "protobuf"

	gogoNamespace = "github.com/gogo/protobuf"
	gogoGenFast   = "protoc-gen-gogofast"
	gogoGenFaster = "protoc-gen-gogofaster"
	gogoGenSlick  = "protoc-gen-gogoslick"

	dirPerm  = 0744
	execPerm = 0744
	filePerm = 0644
)

func latestRelease(ctx context.Context, owner, repo string) (*github.RepositoryRelease, error) {
	releases, _, err := githubClient.Repositories.ListReleases(ctx, owner, repo, list1Options)
	if err != nil {
		return nil, err
	}

	return releases[0], nil
}

func downloadRelease(ctx context.Context, release *github.RepositoryRelease) ([]byte, error) {
	suffix := archAssertSuffix()
	for _, assert := range release.Assets {
		if strings.Contains(assert.GetName(), suffix) {
			return downloadContent(ctx, assert.GetBrowserDownloadURL())
		}
	}

	return nil, errAssertNotFound
}

func archAssertSuffix() string {
	switch runtime.GOOS + "/" + runtime.GOARCH {
	case "windows/amd64":
		return "win64"
	case "windows/386":
		return "win32"
	case "linux/amd64":
		return "linux-x86_64"
	case "linux/386":
		return "linux-x86_32"
	case "darwin/amd64":
		return "osx-x86_64"
	default:
		return ""
	}
}

func downloadContent(ctx context.Context, url string) ([]byte, error) {
	req, _ := http.NewRequestWithContext(ctx, http.MethodGet, url, nil)
	resp, err := httpClient.Do(req)
	if err != nil {
		return nil, err
	}

	defer func() { _ = resp.Body.Close() }()
	return ioutil.ReadAll(resp.Body)
}

func extractCompilers(content []byte, dir string) error {
	return visitCompressFiles(content, func(f *zip.File) error {
		if strings.HasPrefix(f.Name, "bin/") {
			filename := utils.NormalizePath(join(dir, filepath.Base(f.Name)))
			if err := extractFileInto(f, filename, execPerm); err != nil {
				return err
			}
		} else if strings.HasPrefix(f.Name, "include/") {
			filename := utils.NormalizePath(join(dir, f.Name))
			if err := extractFileInto(f, filename, filePerm); err != nil {
				return err
			}
		}

		return nil
	})
}

func extractGoGoProtobuf(content []byte, dir string) error {
	return visitCompressFiles(content, func(f *zip.File) error {
		filename := f.Name[strings.Index(f.Name, "/"):]
		return extractFileInto(f, utils.NormalizePath(join(dir, filename)), filePerm)
	})
}

func visitCompressFiles(content []byte, action func(f *zip.File) error) error {
	rd, err := zip.NewReader(bytes.NewReader(content), int64(len(content)))
	if err != nil {
		return err
	}

	for _, f := range rd.File {
		if !f.FileInfo().IsDir() {
			if err := action(f); err != nil {
				return err
			}
		}
	}
	return nil
}

func extractFileInto(f *zip.File, filename string, perm os.FileMode) error {
	dirname := filepath.Dir(filename)
	if err := os.MkdirAll(dirname, dirPerm); err != nil {
		return err
	}

	rd, err := f.Open()
	if err != nil {
		return err
	}
	defer func() { _ = rd.Close() }()

	fp, err := os.OpenFile(filename, os.O_CREATE|os.O_WRONLY, perm)
	if err != nil {
		return err
	}
	defer func() { _ = fp.Close() }()

	_, err = io.Copy(fp, rd)
	return err
}

func compileCompilerGen(main string, out string) error {
	cmd := exec.Command("go", "build", "-o", out, main)
	_, err := cmd.Output()

	return err
}

func copyGoGoProtobufFiles(src, dst string) error {
	if err := copyFile("gogoproto/gogo.proto", src, join(dst, gogoNamespace)); err != nil {
		return err
	}

	return copyTree(join(src, "protobuf"), join(dst, gogoNamespace))
}

func copyTree(src, dst string) error {
	return filepath.Walk(src, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}

		if !info.IsDir() {
			return copyFile(path[len(src)+1:], src, dst)
		}
		return nil
	})
}

func copyFile(filename, from, to string) error {
	if err := os.MkdirAll(filepath.Dir(join(to, filename)), dirPerm); err != nil {
		return err
	}

	src, err := os.Open(join(from, filename))
	if err != nil {
		return err
	}
	defer func() { _ = src.Close() }()

	dst, err := os.Create(join(to, filename))
	if err != nil {
		return err
	}
	defer func() { _ = dst.Close() }()

	_, err = io.Copy(dst, src)
	return err
}
