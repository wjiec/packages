#!/usr/bin/env bash

protoc --go-netrpc_out=paths=source_relative:. echo.proto
