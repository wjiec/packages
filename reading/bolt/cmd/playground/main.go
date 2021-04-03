package main

import (
	"github.com/boltdb/bolt"
	"os"
	"time"
)

func main() {
	db, err := bolt.Open("./bolt.db", os.FileMode(0777), &bolt.Options{ReadOnly:false, Timeout:time.Second})
	if err != nil {
		panic(err)
	}

	go func() {
		for {
			_ = db.Update(func(tx *bolt.Tx) error {
				time.Sleep(time.Second)
				return nil
			})
		}
	}()

	time.Sleep(time.Minute)
	_ = db.Close()
}
