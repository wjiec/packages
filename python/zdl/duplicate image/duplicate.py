#!/usr/bin/env python
#
# Copyright (C) 2017 DL
#
from __future__ import print_function, with_statement
import os
import math
import argparse
import hashlib
import operator
from PIL import Image
from functools import reduce


class BaseDuplicate(object):
    def __init__(self, path_to_res_dir):
        self._directory = path_to_res_dir

    def duplicate(self, options):
        raise NotImplementedError

    def _result_filter(self, result, *args):
        raise NotImplementedError


class Md5Duplicate(BaseDuplicate):
    def __init__(self, path_to_res_dir):
        super(Md5Duplicate, self).__init__(path_to_res_dir)

    def duplicate(self, options):
        _duplicate_result = {}
        for current_dir, dirs, files in os.walk(self._directory):
            for _file_name in files:
                _md5_instance = hashlib.md5()
                _absolute_path = os.path.join(current_dir, _file_name)

                with open(_absolute_path, 'rb') as fp:
                    while True:
                        buf = fp.read(4096)
                        if not buf:
                            break
                        _md5_instance.update(buf)
                _key = _md5_instance.hexdigest()
                if _key in _duplicate_result:
                    _duplicate_result[_key].append(_absolute_path)
                else:
                    _duplicate_result[_key] = [_absolute_path]
        return self._result_filter(_duplicate_result)

    def _result_filter(self, result, *args):
        return filter(lambda el: len(el[1]) != 1, result.items())


# TODO. multi process
class PhaDuplicate(BaseDuplicate):
    def duplicate(self, options):
        _variance = options.variance
        _resize_width = options.width
        _resize_height = options.height

        if _resize_width * _resize_height > 65536:
            print('Warning: resize picture is too large,'
                  ' may be calculated for a long time,'
                  ' And it takes up a lot of memory')

        _duplicate_result = {}
        for current_dir, dirs, files in os.walk(self._directory):
            for _file_name in files:
                _absolute_path = os.path.join(current_dir, _file_name)
                try:
                    _image = Image.open(_absolute_path)

                    (_o_width, _o_height) = _image.size
                    if _o_width * _o_height > _resize_width * _resize_height:
                        _image = _image.resize((_resize_width, _resize_height))
                    else:
                        _resize_width = _o_width
                        _resize_height = _o_height
                    _gray_image = _image.convert('L')
                    _pixels = list(_gray_image.getdata())
                    _pha_value = []
                    print('Calc difference of {}'.format(_absolute_path))
                    for row in range(_resize_height):
                        _pha_value.append(
                            self._calc_difference(_pixels, _resize_width, row))
                    _pha_value = ''.join(_pha_value)
                    _duplicate_result[_absolute_path] = _pha_value
                except IOError:
                    print('Warning: ignore {}'.format(_absolute_path))
                    continue
        return self._result_filter(_duplicate_result, _variance)

    @staticmethod
    def _calc_difference(pixels, width, row_index):
        _difference = []
        _start_index = width * row_index

        for _rel_index in range(width - 1):
            _abs_index = _start_index + _rel_index
            _difference.append(
                pixels[_abs_index] > pixels[_abs_index + 1])
        return ''.join(
            chr(48 + v) for v in map(lambda x: 1 if x else 0, _difference))

    def _result_filter(self, rst, *args):
        _variance = args[0]
        _duplicate_result = []

        def _compare(s1, s2, border):
            _diff_count = 0
            assert len(s1) == len(s2)

            for index in range(len(s1)):
                if s1[index] != s2[index]:
                    _diff_count += 1
                    if _diff_count > border:
                        return False
            return True

        _keys = rst.keys()
        for _idx in range(len(_keys)):
            for _lo in range(_idx + 1, len(_keys)):
                if _compare(rst[_keys[_idx]], rst[_keys[_lo]], _variance):
                    _duplicate_result.append((_keys[_idx], _keys[_lo]))
            print('{:<8}/{:>8}'.format(_idx + 1, len(_keys)))
        return [('LIMIT: {}'.format(_variance), k) for k in _duplicate_result]


# TODO. multi process
class HistDuplicate(BaseDuplicate):
    def duplicate(self, options):
        _duplicate_result = {}
        for current_dir, dirs, files in os.walk(self._directory):
            for _file_name in files:
                _absolute_path = os.path.join(current_dir, _file_name)

                try:
                    _image = Image.open(_absolute_path)
                    print('Calc Histogram of {}'.format(_absolute_path))
                    _duplicate_result[_absolute_path] = _image.histogram()
                except IOError:
                    continue
        return self._result_filter(_duplicate_result, options.variance)

    def _result_filter(self, rst, *args):
        _variance = args[0]
        _keys = rst.keys()

        _duplicate_result = []
        for _idx in range(len(_keys)):
            for _lo in range(_idx + 1, len(_keys)):
                if self._compare(rst[_keys[_idx]], rst[_keys[_lo]], _variance):
                    _duplicate_result.append((_keys[_idx], _keys[_lo]))
            print('{:<8}/{:>8}'.format(_idx + 1, len(_keys)))
        return [('LIMIT: {}'.format(_variance), k) for k in _duplicate_result]

    @staticmethod
    def _compare(h1, h2, brd):
        result = math.sqrt(
            reduce(
                operator.add,
                list(
                    map(
                        lambda a, b:
                        (a - b) ** 2, h1, h2
                    )
                )
            ) / len(h1)
        )
        return result <= brd


def init_parser():
    parser = argparse.ArgumentParser()

    sub_parsers = parser.add_subparsers()

    _md5_parser = sub_parsers.add_parser('md5', help='Using md5 duplicate')
    _md5_parser.add_argument('directory',
                             type=str,
                             help='resource directory')
    _md5_parser.set_defaults(method=('md5', Md5Duplicate))

    _pha_parser = sub_parsers.add_parser(
        'pha', help='Using perceptual hash algorithm duplicate')
    _pha_parser.add_argument('-w', '--width',
                             type=int,
                             default=64,
                             help='picture width, pixel')
    _pha_parser.add_argument('-H', '--height',
                             type=int,
                             default=64,
                             help='picture height, pixel')
    _pha_parser.add_argument('-v', '--variance',
                             type=int,
                             default=2,
                             help='allow variance')
    _pha_parser.add_argument('directory',
                             type=str,
                             help='resource directory')
    _pha_parser.set_defaults(method=('pha', PhaDuplicate))

    _hist_parser = sub_parsers.add_parser(
        'hist', help='Using Histogram duplicate')
    _hist_parser.add_argument('-v', '--variance',
                              type=int,
                              default=5,
                              help='allow variance')
    _hist_parser.add_argument('directory',
                              type=str,
                              help='resource directory')
    _hist_parser.set_defaults(method=('hist', HistDuplicate))

    return parser


def result_printer(result, file):
    with open(file, 'w') as fp:
        for el in result:
            (val, lst) = el
            if isinstance(val, (tuple, list)):
                for v in val:
                    fp.write('{}\n'.format(v))
            else:
                fp.write('{}\n'.format(val))
            if isinstance(lst, (tuple, list)):
                for item in lst:
                    fp.write('\t{}\n'.format(item))
            else:
                fp.write('{}\n'.format(lst))


def main():
    parser = init_parser()
    options = parser.parse_args()
    if not hasattr(options, 'directory'):
        parser.error('too few arguments')
    _name, _duplicator = options.method
    _directory = options.directory
    if os.path.isdir(_directory):
        _directory = os.path.abspath(_directory)
        _duplicator = _duplicator(_directory)
        _result = _duplicator.duplicate(options)
        result_printer(_result, 'duplicate_{}.txt'.format(_name))
    else:
        raise RuntimeError('{} is not directory'.format(_directory))


if __name__ == '__main__':
    main()
