package buffer

type Buffer struct {
	internal cBuffer
}

func (buf *Buffer) Size() int {
	return cBufferSize(buf.internal)
}

func (buf *Buffer) Data() []byte {
	return cBufferData(buf.internal)
}

func (buf *Buffer) Free() {
	cDeleteBuffer(buf.internal)
	buf.internal = nil
}

func (buf *Buffer) Print() {
	cPrintBuffer(buf.internal)
}

func New(sz int) *Buffer {
	return &Buffer{internal: cNewBuffer(sz)}
}
