ifeq ("$(origin V)", "command line")
	INPUT_V = $(V)
endif

ifeq ($(INPUT_V), 1)
	PREFIX = 'TRUE'
else
	PREFIX = 'FALSE'
endif

main:
	@echo $(PREFIX)
