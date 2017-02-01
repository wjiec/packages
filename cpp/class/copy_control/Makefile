include ../../env.make

BINS = copy_constructor assign_operator destructor \
	   function

all: $(BINS)
.PHONY: all

copy_constructor: copy_constructor.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

assign_operator: assign_operator.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

destructor: destructor.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

function: function.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

