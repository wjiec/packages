include env.make

BINS  = function_type inline_shortString

all: function_type inline_shortString
.PHONY: all

function_type: function_type.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

inline_shortString: inline_shortString.cpp
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	$(ECHO) Clean...
	@-rm $(BINS)
