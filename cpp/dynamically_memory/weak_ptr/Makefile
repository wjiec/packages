include ../../env.make

BINS = sample

all: $(BINS)
.PHONY: all

sample: sample.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

