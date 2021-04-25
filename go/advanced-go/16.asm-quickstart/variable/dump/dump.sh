#!/usr/bin/env bash

go tool compile -S dump.go
rm -rf dump.o
