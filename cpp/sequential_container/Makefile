include ../env.make

BINS = type_alias swap array

all: $(BINS)
.PHONY: all

type_alias: type_alias.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

swap: swap.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

array: array.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

