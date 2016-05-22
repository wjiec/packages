CC    = @g++
FLAGS = -Wall -std=c++11
BINS  = function_type inline_shortString


all: function_type inline_shortString
.PHONY: all

function_type: function_type.cpp
	@echo Compile $@
	$(CC) $(FLAGS) -o $@ $^

inline_shortString: inline_shortString.cpp
	@echo Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@echo Clean...
	@-rm $(BINS)
