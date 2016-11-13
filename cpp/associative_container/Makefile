include ../env.make

BINS  = map

all: $(BINS)
.PHONY: all

map: map.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

.PHONY: clean
clean:
	$(ECHO) Clean...
	@-rm $(BINS)
