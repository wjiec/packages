include ../../../env.make

BINS = constructor

all: $(BINS)
.PHONY: all

constructor: constructor.cpp header.h
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)
