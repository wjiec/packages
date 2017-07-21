<?php
/**
 * Utility Module
 */


/**
 * Class Rtfd_Utils
 */
class Rtfd_Utils {
    /**
     * convert object to string
     *
     * @param $object
     * @return string
     */
    public static function to_string($object) {
        if (is_string($object)) {
            return $object;
        } else if ($object === null) {
            return $object;
        }
        // require convert
        return strval($object);
    }

    /**
     * calc signature
     *
     * @param array $options
     * @return string
     */
    public static function calc_signature(array $options) {
        // options keys
        $values = array_map(function($v) {
            if (is_array($v)) {
                return urldecode(json_encode($v));
            }
            return self::to_string($v);
        }, array_values($options));
        // sort bvy asc
        sort($values, SORT_STRING);
        // calc signature
        return md5(join('~', array(
            // Signature keys
            _rtfd_signature_key_,
            // options keys
            join('-', $values),
            // timestamp
            $options['timestamp']
        )));
    }

    /**
     * calc signature and verify correct
     *
     * @param $signature
     * @param array $options
     * @return bool
     */
    public static function verify_signature($signature, array $options) {
        // calc signature
        $calc_signature = self::calc_signature($options);
        // verify the same to input_signature
        return $calc_signature === $signature;
    }

    /**
     * check api exists
     *
     * @param $api_name
     * @return bool
     */
    public static function class_exists($api_name) {
        $class_file = str_replace(array('\\', '_'), '/', $api_name) . '.php';
        // check file exists, because class auto-loader using include_path, so cannot using `is_file` check file exists
        if (file_get_contents($class_file, true, null, 0, 1) && class_exists($api_name)) {
            return true;
        }
        return false;
    }

    /**
     * check keys is in array
     *
     * @param array $keys
     * @param array $array
     * @return bool
     */
    public static function array_keys_exists(array $keys, array $array) {
        foreach ($keys as $key) {
            if (!array_key_exists($key, $array)) {
                Rtfd_Response::debug_output($keys);
                return false;
            }
        }
        return true;
    }

    /**
     * convert to correct encoding
     *
     * @param string $string
     * @param string $source_encoding
     * @throws Rtfd_Exception_FatalError
     * @return string
     */
    public static function Text($string, $source_encoding = 'gb2312') {
        if (json_encode($string) === false) {
            try {
                return trim(iconv($source_encoding, 'utf-8', $string));
            } catch (Exception $e) {
                throw new Rtfd_Exception_FatalError('convert encoding error occurs',
                    'Gm:Utils:Text');
            }
        }
        // result
        return $string;
    }
}