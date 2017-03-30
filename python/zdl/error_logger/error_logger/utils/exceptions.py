#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#

class LoggerWarning(Exception):
    pass


class DeamonError(Exception):
    pass


class FatalError(Exception):
    pass


class ConfigError(Exception):
    pass


class ForbiddenIpError(Exception):
    pass


class OperationalError(Exception):
    pass
