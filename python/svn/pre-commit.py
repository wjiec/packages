#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import re
import sys
reload(sys)
sys.setdefaultencoding('utf-8')


class SubVersionChecker(object):
    def __init__(self, txn, repos):
        self.repos = repos
        self.txn = txn

    def get_commit_logs(self):
        commit_log_fd = os.popen('svnlook changed -t {txn} {repos}'.format(
            txn=self.txn, repos=self.repos))
        commit_logs = commit_log_fd.readlines()
        return commit_logs

    def check(self):
        commit_logs = self.get_commit_logs()
        for commit_log in commit_logs:
            matches = re.match('^[AUD]\s*(.*)$', commit_log)
            file_name = matches.group(1)

            file_name = file_name.replace('{U+', '\\u')
            file_name = file_name.replace('}', '')
            file_name = file_name.encode('utf-8').decode('unicode-escape')
            cmd = 'export LANG=en_US.UTF-8;svnlook cat -t {} {} {}'.format(
                self.txn, self.repos, file_name)

            process = os.popen(cmd).read()
            result = re.search(r'http:.+clouddn.+\.png', process)


if __name__ == '__main__':
    os.dup2(sys.stderr.fileno(), sys.stdout.fileno())

    checker = SubVersionChecker(sys.argv[2], sys.argv[1])
    checker.check()
