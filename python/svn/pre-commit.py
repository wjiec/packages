#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Copyright (C) 2017
import os
import re
import sys
import locale
import subprocess


# stdout >> stderr
os.dup2(sys.stderr.fileno(), sys.stdout.fileno())
# reset shell coding
os.system('export LANG=en_US.UTF-8')

svn_txn = sys.argv[2]
svn_repos = sys.argv[1]

commit_log_fd = os.popen('svnlook changed -t {txn} {repos}'.format(
    txn=svn_txn, repos=svn_repos))
commit_logs = commit_log_fd.readlines()

for commit_log in commit_logs:
    matches = re.match('^[AUD]\s*(.*)$', commit_log)
    file_name = matches.group(1)

    file_name = file_name.replace('{U+', '\\u')
    file_name = file_name.replace('}/', '/')

    file_name = file_name.encode('utf-8').decode('unicode-escape')


    cmd = 'svnlook cat -t {} {} {}'.format(svn_txn, svn_repos, file_name)
    subprocess.call(['svnlook', 'cat', '-t', svn_txn, svn_repos, file_name])

    #if re.search('', contents):
    #    print('Commit rejected, because {} contents invalid'.format(file_name))
    #    exit(1)


exit(1)
