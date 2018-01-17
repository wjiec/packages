#!/usr/bin/env python
#
# Copyright (C) 2018 JaysonWang
#
import hashlib


def evp_bytes_to_key(password, key_len, iv_len,
                     salt=None, count=1, hash_func=hashlib.md5):
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
        return None, None

    data = password + (salt if salt else b'')
    message_digest_array = [data]
    for _ in range(count):
        hash_inst = hash_func()
        hash_inst.update(message_digest_array[0])
        message_digest_array[0] = hash_inst.digest()

    while len(b''.join(message_digest_array)) < (key_len + iv_len):
        hash_inst = hash_func()
        hash_inst.update(message_digest_array[-1] + data)
        message_digest_array.append(hash_inst.digest())

    result = b''.join(message_digest_array)
    return result[:key_len], result[key_len:key_len + iv_len]


if __name__ == '__main__':
    print(evp_bytes_to_key(b'my-secret-password', 32, 16))
