package main

import (
	"assembler/internal/assembler/command"
	"assembler/pkg/commander"
	"context"
	"os"
)

func main() {
	_ = commander.Register(&command.Assemble{})
	_ = commander.Register(&command.Disassemble{})

	os.Exit(int(commander.Execute(context.Background())))
}
