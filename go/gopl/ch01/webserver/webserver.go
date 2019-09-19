package main

import (
	"image/color"
	"log"
	"net/http"
	"strconv"

	"github.com/nosjay/gopl/ch01/lissajous"
)

func main() {
	http.HandleFunc("/", func(w http.ResponseWriter, request *http.Request) {
		cycles, _ := strconv.Atoi(request.URL.Query().Get("cycles"))
		l := lissajous.Lissajous{
			Cycles:  cycles,
			Res:     0.0001,
			Size:    160,
			Frames:  64,
			Delay:   1,
			Palette: []color.Color{color.Black, color.RGBA{R: 0x00, G: 0xff, B: 0x00, A: 0xff}},
		}

		_ = l.Write(w)
	})

	log.Fatal(http.ListenAndServe(":8898", nil))
}
