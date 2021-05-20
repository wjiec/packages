#!/usr/bin/env bash

openssl genrsa -out server.key 2048
openssl req -new -x509 -days 365 -subj "/C=GB/L=China/O=grpc-server/CN=server.com" -key server.key -out server.crt

openssl genrsa -out client.key 2048
openssl req -new -x509 -days 365 -subj "/C=GB/L=China/O=grpc-client/CN=client.com" -key client.key -out client.crt

# with ca
openssl genrsa -out ca.key 2048
openssl req -new -x509 -days 365 -subj "/C=GB/L=China/O=grpc-server/CN=ca.com" -key ca.key -out ca.crt

openssl req -new -days 365 -subj "/C=GB/L=China/O=grpc-server/CN=server.com" -key server.key -out server-ca.csr
openssl x509 -req -sha256 -CA ca.crt -CAkey ca.key -CAcreateserial -days 365 -in server-ca.csr -out server-ca.crt

openssl req -new -days 365 -subj "/C=GB/L=China/O=grpc-server/CN=client.com" -key client.key -out client-ca.csr
openssl x509 -req -sha256 -CA ca.crt -CAkey ca.key -CAcreateserial -days 365 -in client-ca.csr -out client-ca.crt
