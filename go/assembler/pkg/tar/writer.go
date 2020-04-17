package tar

import (
	"archive/tar"
	"io"
	"log"
	"os"
	"path/filepath"
	"strings"
)

// ArchiveWriter represents a writer tarball-archive
type ArchiveWriter struct {
	writer   *tar.Writer
	excludes []string
}

// Add add file or folder into tarball-archive
func (a *ArchiveWriter) Add(abs string, rel string) error {
	info, err := os.Stat(abs)
	if err != nil || os.IsNotExist(err) {
		return err
	}

	if info.IsDir() {
		return a.AddFolder(abs, rel)
	}
	return a.AddFile(abs, rel, nil)
}

// AddFile add file into tarball-archive
func (a *ArchiveWriter) AddFile(abs string, rel string, info os.FileInfo) error {
	for _, exclude := range a.excludes {
		if len(exclude) > 0 && strings.Contains(abs, exclude) {
			log.Printf("Skip %s(exclude: %s)\n", abs, exclude)
			return nil
		}
	}

	if info == nil {
		var err error
		if info, err = os.Stat(abs); err != nil {
			return err
		}
	}

	if !info.Mode().IsRegular() {
		return nil
	}

	log.Printf("Add %s into %s", abs, rel)
	header, err := tar.FileInfoHeader(info, info.Name())
	if err != nil {
		return err
	}

	header.Name = rel
	if err := a.writer.WriteHeader(header); err != nil {
		return err
	}

	file, err := os.Open(abs)
	if err != nil {
		return err
	}

	defer func() {
		_ = file.Close()
	}()

	if _, err := io.Copy(a.writer, file); err != nil {
		return err
	}

	return nil
}

// AddFolder add folder into tarball-archive
func (a *ArchiveWriter) AddFolder(abs string, rel string) error {
	base := strings.Trim(rel, "\\/")
	return filepath.Walk(abs, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}

		filename := base + strings.TrimLeft(strings.TrimPrefix(path, abs), "\\/")
		return a.AddFile(path, strings.Replace(filename, "\\", "/", -1), info)
	})
}

// Flush flush the tarball-archive stream
func (a *ArchiveWriter) Flush() error {
	return a.writer.Flush()
}

// NewWriter create a tarball archive
func NewWriter(writer io.Writer, excludes []string) *ArchiveWriter {
	return &ArchiveWriter{
		excludes: excludes,
		writer:   tar.NewWriter(writer),
	}
}
