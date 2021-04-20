#!/usr/bin/env bash

gcc -I../ main.c -L../ -lmain -lpthread
LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH ./a.out
