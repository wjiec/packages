include ../../env.make

BINS = accumulate

all: $(BINS)
.PHONY: all

accumulate: accumulate.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

