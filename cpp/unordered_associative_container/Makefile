include ../env.make

BINS  = unordered_map bucket

all: $(BINS)
.PHONY: all

unordered_map: unordered_map.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

bucket: bucket.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

.PHONY: clean
clean:
	$(ECHO) Clean...
	@-rm $(BINS)
