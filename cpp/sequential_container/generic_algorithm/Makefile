include ../../env.make

BINS = accumulate fill_n back_inserter lambda \
	   elim

all: $(BINS)
.PHONY: all

accumulate: accumulate.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

fill_n: fill_n.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^ 

back_inserter: back_inserter.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

lambda: lambda.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

elim: elim.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^



.PHONY: clean
clean:
	@-rm -v $(BINS)

