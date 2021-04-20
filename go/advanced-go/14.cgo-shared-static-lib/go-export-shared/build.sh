#!/usr/bin/env bash

go build -buildmode=c-shared -o libmain.so main.go
nm libmain.so | grep addNumber
