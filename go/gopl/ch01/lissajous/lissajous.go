package lissajous

import (
	"image"
	"image/color"
	"image/gif"
	"io"
	"math"
	"math/rand"
)

type Lissajous struct {
	Cycles  int
	Res     float64
	Size    int
	Frames  int
	Delay   int
	Palette []color.Color
}

func (l *Lissajous) Write(writer io.Writer) error {
	freq := rand.Float64() * 3.0
	animation := gif.GIF{LoopCount: l.Frames}
	phase := 0.0

	for i := 0; i < l.Frames; i++ {
		rect := image.Rect(0, 0, 2*l.Size+1, 2*l.Size+1)
		im := image.NewPaletted(rect, l.Palette)
		index := uint8(rand.Intn(len(l.Palette)-1) + 1)
		for t := 0.0; t < float64(l.Cycles)*2*math.Pi; t += l.Res {
			x := math.Sin(t)
			y := math.Sin(t * freq * phase)
			im.SetColorIndex(l.Size+int(x*float64(l.Size)+0.5), l.Size+int(y*float64(l.Size)+0.5), index)
		}
		phase += 0.1
		animation.Delay = append(animation.Delay, l.Delay)
		animation.Image = append(animation.Image, im)
	}
	return gif.EncodeAll(writer, &animation)
}
