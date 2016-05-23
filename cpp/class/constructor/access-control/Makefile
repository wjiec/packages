include ../../../env.make

BINS = access_control

all: $(BINS)
.PHONY: all

access_control: access.cpp access.h
	$(ECHO) Compile $@
	$(CC) $(FLAGS) -o $@ $^

.PHONY: clean
clean:
	@-rm -v $(BINS)
