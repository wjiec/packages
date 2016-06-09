include ../env.make

BINS = const_member_function friend type_member \
	   member_initialize

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

member_initialize: member_initialize.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

