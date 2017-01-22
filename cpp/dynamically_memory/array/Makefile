include ../../env.make

BINS = sample

all: $(BINS)
.PHONY: all

sample: sample.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

