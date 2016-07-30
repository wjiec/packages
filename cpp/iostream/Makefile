include ../env.make

BINS = CPPPrimer_8.4 ifstream state ofstream \
	   istringstream ostringstream

all: $(BINS)
.PHONY: all

CPPPrimer_8.4: C++Primer_8.4.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

ifstream: ifstream.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

state: state.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

ofstream: ofstream.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

istringstream: istringstream.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

ostringstream: ostringstream.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^



.PHONY: clean
clean:
	@-rm -v $(BINS)

