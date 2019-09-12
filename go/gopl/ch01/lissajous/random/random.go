package main

import (
	"image/color"
	"log"
	"math/rand"
	"net/http"
	"os"

	"github.com/nosjay/gopl/ch01/lissajous"
)

func NewLissajous(palette []color.Color) lissajous.Lissajous {
	return lissajous.Lissajous{
		Cycles:  3,
		Res:     0.0001,
		Size:    160,
		Frames:  64,
		Delay:   1,
		Palette: palette,
	}
}

func main() {
	palette := []color.Color{color.Black}
	for i := 0; i < 255; i++ {
		palette = append(palette, color.RGBA{
			R: uint8(rand.Intn(255)),
			G: uint8(rand.Intn(255)),
			B: uint8(rand.Intn(255)),
			A: 0xff,
		})
	}

	l := NewLissajous(palette)
	if len(os.Args) > 1 && os.Args[1] == "web" {
		http.HandleFunc("/", func(w http.ResponseWriter, request *http.Request) {
			_ = l.Write(w)
		})
		log.Fatal(http.ListenAndServe(":8088", nil))
	}
	_ = l.Write(os.Stdout)
}
