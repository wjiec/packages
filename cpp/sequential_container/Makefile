include ../env.make

BINS = type_alias swap array \
	   push_back push_front insert \
	   emplace

all: $(BINS)
.PHONY: all

type_alias: type_alias.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

swap: swap.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

array: array.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

push_back: push_back.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

push_front: push_front.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

insert: insert.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

emplace: emplace.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^


.PHONY: clean
clean:
	@-rm -v $(BINS)

