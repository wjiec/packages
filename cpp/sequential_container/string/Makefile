include ../../env.make

BINS = find 

all: $(BINS)
.PHONY: all

find: find.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

