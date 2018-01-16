#!/usr/bin/env python
#
# Copyright (C) 2018 JaysonWang
#
import hashlib


def EVP_BytesToKey(password, salt, key_len, iv_len):
    """ Pseude-Code
    M[] is an array of message digests
    MD() is the message digest function

        M[0] = MD(data . salt);
        for (i=1; i < count; i++)
            M[0] = MD(M[0]);

        i=1
        while (data still needed for key and iv)
            M[i] = MD(M[i-1] . data . salt);
            for (i = 1; i < count; i++)
                M[i] = MD(M[i]);
            i++;

    If the salt is NULL, it is not used.
    The digests are concatenated together.
    M = M[0] . M[1] . M[2] .......
    """
    if not password:
        return None

    M = []

    i = 0
    while len(b''.join(M)) < (key_len + iv_len):
        md5_hash = hashlib.md5()

        if len(M) == 0:
            md5_hash.update(password + salt if salt else b'')
            M.append(md5_hash.digest())
            continue

        md5_hash.update(M[i - 1] + password + salt if salt else b'')
        M.append(md5_hash.digest())

        i += 1

    key = b''.join(M)[:key_len]
    iv = b''.join(M)[key_len:key_len + iv_len]
    return key, iv


if __name__ == '__main__':
    print(EVP_BytesToKey(b'HelloWorld', b'', 16, 16))
