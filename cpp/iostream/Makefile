include ../env.make

BINS = CPPPrimer_8.4 
all: $(BINS)
.PHONY: all

CPPPrimer_8.4: C++Primer_8.4.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^
.PHONY: clean
clean:
	@-rm -v $(BINS)

