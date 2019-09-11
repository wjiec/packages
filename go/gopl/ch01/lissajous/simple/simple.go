package main

import (
	"image/color"
	"log"
	"net/http"
	"os"

	"github.com/nosjay/gopl/ch01/lissajous"
)

var (
	l = lissajous.Lissajous{
		Cycles:  3,
		Res:     0.0001,
		Size:    160,
		Frames:  64,
		Delay:   1,
		Palette: []color.Color{color.Black, color.RGBA{R: 0x00, G: 0xff, B: 0x00, A: 0xff}},
	}
)

func main() {
	if len(os.Args) > 1 && os.Args[1] == "web" {
		http.HandleFunc("/", func(w http.ResponseWriter, request *http.Request) {
			_ = l.Write(w)
		})
		log.Fatal(http.ListenAndServe(":8088", nil))
	}
	_ = l.Write(os.Stdout)
}
