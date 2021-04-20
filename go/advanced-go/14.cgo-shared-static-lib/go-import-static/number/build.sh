#!/usr/bin/env bash

gcc -c -o number.o number.c
ar rcs linbnumber.a number.o
