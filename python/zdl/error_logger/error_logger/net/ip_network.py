#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
import struct
import socket
from error_logger.utils import logger, generic


def inet_pton(family, address):
    if hasattr(socket, 'inet_pton'):
        return socket.inet_pton(family, address)
    address = generic.to_string(address)
    if family == socket.AF_INET:
        return socket.inet_aton(address)
    elif family == socket.AF_INET6:
        if '.' in address:  # a v4 address
            v4_address = address[address.rindex(':') + 1:]
            v4_address = socket.inet_aton(v4_address)
            v4_address = map(lambda x: ('%02X' % ord(x)), v4_address)
            v4_address.insert(2, ':')
            new_address = address[:address.rindex(':') + 1] + ''.join(
                v4_address)
            return inet_pton(family, new_address)
        _bytes = [0] * 8  # 8 groups
        groups = address.split(':')
        for i, v in enumerate(groups):
            if v:
                _bytes[i] = int(v, 16)
            else:
                for j, w in enumerate(groups[::-1]):
                    if w:
                        _bytes[7 - j] = int(w, 16)
                    else:
                        break
                break
        return b''.join((chr(i // 256) + chr(i % 256)) for i in _bytes)
    else:
        raise RuntimeError("What family?")


def is_ip(address):
    for family in (socket.AF_INET, socket.AF_INET6):
        try:
            if type(address) != str:
                address = address.decode('utf8')
            inet_pton(family, address)
            return family
        except (TypeError, ValueError, OSError, IOError):
            pass
    return False


class IPNetwork(object):
    address_length = {socket.AF_INET: 32, socket.AF_INET6: 128, False: 0}

    def __init__(self, address=None):
        self._network_list_v4 = []
        self._network_list_v6 = []
        if type(address) == str:
            address = address.split(',')
            list(map(self.add_network, address))

    def add_network(self, address, prefix_length=None):
        if address is "":
            return

        ip, prefix_size, address_family = self._get_ip_prefix(address)
        if isinstance(prefix_length, int):
            prefix_size = \
                min(prefix_length, self.address_length[address_family])

        if address_family is socket.AF_INET:
            self._network_list_v4.append((ip, prefix_size))
        else:
            self._network_list_v6.append((ip, prefix_size))

    def __contains__(self, address):
        address_family = is_ip(address)
        if address_family is socket.AF_INET:
            ip, = struct.unpack("!I", socket.inet_aton(address))
            return any(map(lambda n_ps: n_ps[0] == ip >> n_ps[1],
                           self._network_list_v4))
        elif address_family is socket.AF_INET6:
            hi, lo = struct.unpack("!QQ", inet_pton(address_family, address))
            ip = (hi << 64) | lo
            return any(map(lambda n_ps: n_ps[0] == ip >> n_ps[1],
                           self._network_list_v6))
        else:
            return False

    def remove(self, address):
        ip, prefix_size, address_family = self._get_ip_prefix(address)

        if address_family is socket.AF_INET:
            self._network_list_v4.remove((ip, prefix_size))
        elif address_family is socket.AF_INET6:
            self._network_list_v6.remove((ip, prefix_size))
        else:
            return None

    @staticmethod
    def _get_ip_prefix(address):
        block = address.split('/')
        address_family = is_ip(block[0])
        address_len = IPNetwork.address_length[address_family]

        if address_family is socket.AF_INET:
            ip, = struct.unpack("!I", socket.inet_aton(block[0]))
        elif address_family is socket.AF_INET6:
            hi, lo = struct.unpack("!QQ", inet_pton(address_family, block[0]))
            ip = (hi << 64) | lo
        else:
            raise Exception("Not a valid CIDR notation: %s" % address)

        if len(block) is 1:
            prefix_size = 0
            while (ip & 1) == 0 and ip is not 0:
                ip >>= 1
                prefix_size += 1
        elif block[1].isdigit() and int(block[1]) <= address_len:
            prefix_size = address_len - int(block[1])
            ip >>= prefix_size
        else:
            raise Exception("Not a valid CIDR notation: %s" % address)

        return ip, prefix_size, address_family
