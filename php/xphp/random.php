<?php
/**
 * 为PHP补充非常常用的方法和对一些官方方法的简化.
 *
 * 本文件为数组相关方法的集合
 *
 *
 * @package   Jayson\XPHP
 * @author    Jayson <jayson@yycfd.com>
 * @copyright Copyright (C) 2019 Jayson
 * @license   MIT License
 */
namespace Jayson\XPHP;


/**
 * 方法声明守卫: 防止方法被重复声明导致报错
 */
if (!defined('JAYSON_XPHP_RANDOM_LOADED')) {
    define('JAYSON_XPHP_RANDOM_LOADED', 'yes');


    /**
     * 以更好的方式生成一个随机数
     *  随机数取值区间为 $min <= x <= $max
     *
     * @param int $min
     * @param int $max
     * @return int
     */
    function random_int_x(int $min, int $max): int {
        try {
            return \random_int($min, $max);
        } catch (\Exception $e) {
            mt_srand(intval(microtime(true) * 1000));
            return \mt_rand($min, $max);
        }
    }


    /**
     * 生成随机字节序列(无异常抛出)
     *
     * @param int $length
     * @return string
     */
    function random_bytes_x(int $length): string {
        try {
            return random_bytes($length);
        } catch (\Throwable $e) {
            $s = '';
            for ($index = 0; $index < $length; ++$index) {
                $s .= chr(random_int_x(0, 255));
            }
            return $s;
        }
    }


    /**
     * 字符集常量定义
     */
    define('X_RANDOM_CHARSET_UPPER', 1 << 1); // 大写字母
    define('X_RANDOM_CHARSET_LOWER', 1 << 2); // 小写字母
    define('X_RANDOM_CHARSET_DIGIT', 1 << 3); // 数字
    define('X_RANDOM_CHARSET_SYMBOL', 1 << 4); // 符号(-_)
    define('X_RANDOM_CHARSET_LETTER', X_RANDOM_CHARSET_UPPER | X_RANDOM_CHARSET_LOWER); // 大小写字幕
    define('X_RANDOM_CHARSET_STANDARD', X_RANDOM_CHARSET_LETTER | X_RANDOM_CHARSET_DIGIT);
    define('X_RANDOM_CHARSET_FULL', X_RANDOM_CHARSET_STANDARD | X_RANDOM_CHARSET_SYMBOL);


    /**
     * 生成一个随机字符串, 可选已提供字符集或者自定义字符集
     *
     * @param int $length
     * @param int $flags
     * @param null|string $charset
     * @return string
     */
    function random_string(int $length, int $flags = X_RANDOM_CHARSET_STANDARD, ?string $charset = null): string {
        $random_charset = $charset ?? '';
        while ($flags !== 0) {
            switch (true) {
                case !!($flags & X_RANDOM_CHARSET_UPPER):
                    $random_charset .= 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
                    $flags &= ~X_RANDOM_CHARSET_UPPER;
                    break;
                case !!($flags & X_RANDOM_CHARSET_LOWER):
                    $random_charset .= 'abcdefghijklmnopqrstuvwxyz';
                    $flags &= ~X_RANDOM_CHARSET_LOWER;
                    break;
                case !!($flags & X_RANDOM_CHARSET_DIGIT):
                    $random_charset .= '0123456789';
                    $flags &= ~X_RANDOM_CHARSET_DIGIT;
                    break;
                case !!($flags & X_RANDOM_CHARSET_SYMBOL):
                    $random_charset .= '-_';
                    $flags &= ~X_RANDOM_CHARSET_SYMBOL;
                    break;
            }
        }

        $random_charset = join('', array_unique(str_split($random_charset)));
        if (mb_strlen($random_charset) <= $length) {
            return str_shuffle($random_charset);
        }

        for ($s = ''; mb_strlen($s) <= $length;) {
            $s .= str_shuffle($random_charset);
        }
        return mb_substr($s, 0, $length);
    }


}
