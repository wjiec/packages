include ../env.make

BINS = 

all: $(BINS)
.PHONY: all

: .cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

