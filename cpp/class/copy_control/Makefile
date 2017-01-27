include ../../env.make

BINS = copy_constructor

all: $(BINS)
.PHONY: all

copy_constructor: copy_constructor.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

