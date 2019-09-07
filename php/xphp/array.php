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
if (!defined('JAYSON_XPHP_ARRAY_LOADED')) {
    define('JAYSON_XPHP_ARRAY_LOADED', 'yes');


    //
    // 1. PHP的参数采用写时复制技术, 只有在修改参数的时候才会复制一份出来.
    // 所以在传递数组的时候, 只要不修改数组中的值, 就不会发送大数组拷贝.
    //
    // 2. 当键为纯数字字符串时, PHP引擎会自动将这个字符串转换成整形数值.
    //


    /**
     * 使用`.`或者自定义分隔符访问多维数组, 当指定的键不存在时, 可以指定需要返回的默认值
     *  array_at($data, 'a.b.c.d') // $data['a']['b']['c']['d']
     *
     *  - 其中数字会被自动转换为整形, 其他类型键请使用数组形式查询
     *      array($data, 'a.0.1.d') // $data['a'][0][1]['d']
     *
     *  - 支持自定义分隔符和找不到时的默认值(null)
     *      array_at($data, 'a.b>c.d', 'default_value', '>') // $data['a.b']['c.d'] ?? 'default_value'
     *
     *  - 支持使用数组形式进行查询
     *      array_at($data, array('a.b', 'c.d'), 'default_value') // $data['a.b']['c.d'] ?? 'default_value'
     *
     *
     * @param array $object
     * @param string|array $key
     * @param mixed $default
     * @param string $delimiter
     * @return mixed
     */
    function array_at(array $object, $key, $default = null, string $delimiter = '.') {
        $query_paths = $key;
        if (!is_array($key)) {
            $query_paths = explode($delimiter, strval($key));
        }

        $value = $object;
        foreach ($query_paths as $key) {
            if (!isset($value[$key])) {
                return $default;
            }
            $value = $value[$key];
        }

        return $value;
    }


    /**
     * 用于判断一个数组是是否包含有指定的键
     *  array_must($data, 'key')
     *
     *  - 支持传入多个键检查
     *      array_must($data, 'key1', 'key2', 1, 2)
     *
     *  - 支持使用`.`检查多维数组(如果键中含有.冲突了, 可以传入数组进行分隔)
     *      array_must($data, 'k1.0.k111') // $data['k1'][0]['k111']
     *      array_must($data, array('a.b', 'c.d')) // $data['a.b']['c.d']
     *
     *
     * @param array $object
     * @param mixed ...$keys
     * @return bool
     */
    function array_must(array $object, ...$keys): bool {
        $not_exists = new \stdClass();
        foreach ($keys as $key) {
            if (array_at($object, $key, $not_exists) === $not_exists) {
                return false;
            }
        }
        return true;
    }

    /**
     * 从一个多维数组中提取指定的key组成一个新的数组
     *  array_pick($data, 'id')
     *      // input: array(array('id' => 1, 'name' => 'a'), array('id' => 2, 'name' => 'b'))
     *      // output: array(1, 2)
     *
     *
     * @param array $object
     * @param mixed ...$keys
     * @return array
     */
    function array_pick(array $object, ...$keys): array {
        return array_map(function(array $item) use ($keys) {
            if (count($keys) === 1) {
                return array_at($item, current($keys));
            }

            $result = array();
            foreach ($keys as $key) {
                $result[$key] = array_at($item, $key);
            }
            return $result;
        }, $object);
    }


}

