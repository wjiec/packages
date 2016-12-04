include ../../env.make

BINS = sample scope StrBlob

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

.PHONY: clean
clean:
	@-rm -v $(BINS)

