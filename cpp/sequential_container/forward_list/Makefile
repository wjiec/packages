include ../../env.make

BINS = forward_list 

all: $(BINS)
.PHONY: all

forward_list: forward_list.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)

