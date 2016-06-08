include ../env.make

BINS = const_member_function friend

all: $(BINS)
.PHONY: all

const_member_function: const_member_function.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

friend: friend.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

