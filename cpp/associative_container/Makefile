include ../env.make

BINS  = map set multi_set custom_set pair \
		multi_access copy insert insert_return \
		find_access is_exists word_transform

all: $(BINS)
.PHONY: all

map: map.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

set: set.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

multi_set: multi_set.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

custom_set: custom_set.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

pair: pair.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

multi_access: multi_access.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

copy: copy.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

insert: insert.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

insert_return: insert_return.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

find_access: find_access.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

is_exists: is_exists.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

word_transform: word_transform.cpp
	$(ECHO) Compile $@ ...
	$(CC) $(FLAGS) -Wno-unused-variable -o $@ $^

.PHONY: clean
clean:
	$(ECHO) Clean...
	@-rm $(BINS)
