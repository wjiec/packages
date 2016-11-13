include ../../env.make

BINS = inserter istream_iterator istream_iterator_example \
		stream_iterator_example

all: $(BINS)
.PHONY: all

inserter: inserter.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

istream_iterator: istream_iterator.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

istream_iterator_example: istream_iterator_example.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

stream_iterator_example: stream_iterator_example.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

