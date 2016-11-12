include ../../env.make

BINS = accumulate fill_n back_inserter lambda \
	   elim find_if lambda_capture lambda equal

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

find_if: find_if.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

lambda_capture: lambda_capture.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

equal: equal.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^


.PHONY: clean
clean:
	@-rm -v $(BINS)

