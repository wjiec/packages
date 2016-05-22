include ../env.make

BINS = function_pointer

all: $(BINS)
.PHONY: all

function_pointer: function_pointer.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	$(ECHO) Clean...
	@-rm -v $(BINS)
