include ../../env.make

BINS = sample scope StrBlob process deleter

all: $(BINS)
.PHONY: all

sample: sample.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

scope: scope.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

StrBlob: StrBlob.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

process: process.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

deleter: deleter.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

