#!/usr/bin/env bash

protoc --go_out=plugins=grpc,paths=source_relative:. pubsub.proto
