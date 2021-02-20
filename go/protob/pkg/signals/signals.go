package signals

import (
	"os"
	"os/signal"
	"syscall"
)

func init() {
	sig := make(chan os.Signal, 1)
	signal.Notify(sig, syscall.SIGTERM, syscall.SIGQUIT, syscall.SIGKILL, syscall.SIGINT)
	go func() {
		<-sig
		signal.Stop(sig)
	}()
}

func OnExit(f func()) {
}
