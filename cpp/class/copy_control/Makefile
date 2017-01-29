include ../../env.make

BINS = copy_constructor assign_operator

all: $(BINS)
.PHONY: all

copy_constructor: copy_constructor.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

assign_operator: assign_operator.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

