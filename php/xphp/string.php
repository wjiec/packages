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
if (!defined('JAYSON_XPHP_STRING_LOADED')) {
    define('JAYSON_XPHP_STRING_LOADED', 'yes');


    /**
     * 判断一个字符串是否是以另一个字符串开头
     *
     * @param string $subject
     * @param string $needle
     * @return bool
     */
    function str_startwith(string $subject, string $needle): bool {
        return mb_strpos($subject, $needle) === 0;
    }


    /**
     * 判断一个字符是否以另一个字符串结尾
     *
     * @param string $subject
     * @param string $needle
     * @return bool
     */
    function str_endwith(string $subject, string $needle): bool {
        return mb_strpos($subject, $needle) === mb_strlen($subject) - mb_strlen($needle);
    }


    /**
     * 判断一个字符串中是否包含另一个字符串
     *
     * @param string $subject
     * @param string $needle
     * @return bool
     */
    function str_includes(string $subject, string $needle): bool {
        return mb_strpos($subject, $needle) !== false;
    }


}
