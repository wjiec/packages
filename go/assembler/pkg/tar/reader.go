package tar

import (
	"archive/tar"
	"io"
	"os"
	"path/filepath"
)

// ArchiveReader represents a reader for tarball-archive
type ArchiveReader struct {
	reader *tar.Reader
}

// Extra extra data into dst
func (a *ArchiveReader) Extra(dst string) error {
	for {
		header, err := a.reader.Next()
		if header == nil || err != nil {
			if err == io.EOF {
				break
			}
			return err
		}

		abs := filepath.Join(dst, header.Name)
		switch header.Typeflag {
		case tar.TypeDir:
			if err := a.createDir(abs); err != nil {
				return err
			}
		case tar.TypeReg:
			if err := a.createDir(filepath.Dir(abs)); err != nil {
				return err
			}

			file, err := os.Create(abs)
			if err != nil {
				return err
			}

			if _, err := io.Copy(file, a.reader); err != nil {
				return err
			}

			_ = file.Close()
		}
	}

	return nil
}

func (a *ArchiveReader) createDir(dir string) error {
	if _, err := os.Stat(dir); err != nil {
		if err := os.MkdirAll(dir, os.ModeDir); err != nil {
			return err
		}
	}
	return nil
}

// NewReader create a reader for tarball-archive
func NewReader(reader io.Reader) *ArchiveReader {
	return &ArchiveReader{
		reader: tar.NewReader(reader),
	}
}
