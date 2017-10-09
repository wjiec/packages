#!/usr/bin/env python
#
# Copyright (C) 2017
#
import os
import sys


class SubVersionChecker(object):

    def __init__(self, repos, txn):
        self.repos = repos
        self.txn = txn

    def get_modify_files(self):
        output = os.popen('svnlook changed -t {txn} {repos}'.format(
            txn=self.txn, repos=self.repos))
        print(output.read())

        output = os.popen('svnlook cat -t {txn} {repos} {file}'.format(
            txn=self.txn, repos=self.repos, file='hello.md'))

        if output.read() == '12345':
            exit(1)


if __name__ == '__main__':
    # stdout >> stderr
    os.dup2(sys.stderr.fileno(), sys.stdout.fileno())

    checker = SubVersionChecker(*sys.argv[1:])
    checker.get_modify_files()
