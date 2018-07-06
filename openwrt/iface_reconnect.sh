#!/bin/sh

env -i /sbin/ifup $1 >/dev/null 2>/dev/null
