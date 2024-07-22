all: chip-build build/chip/tda61.chip.json build/chip/tda61.chip.wasm

chip-build:
	mkdir -p build/chip

build/chip/tda61.chip.wasm: tda61/tda61.chip.c
	/Users/jayson/Sdk/wasi-sdk-22.0/bin/clang --target=wasm32-unknown-wasi --sysroot ~/Sdk/wasi-libc/sysroot -nostartfiles -Wl,--import-memory -Wl,--export-table -Wl,--no-entry -Werror -o $@ $^

build/chip/tda61.chip.json: tda61/tda61.chip.json
	cp $^ $@
