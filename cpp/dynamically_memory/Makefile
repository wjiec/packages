include ../env.make

BINS = new

all: $(BINS)
.PHONY: all

new: new.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

