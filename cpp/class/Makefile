include ../env.make

BINS = const_member_function friend type_member

all: $(BINS)
.PHONY: all

const_member_function: const_member_function.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

friend: friend.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

type_member: type_member.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

