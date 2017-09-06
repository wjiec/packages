<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr\Lib;


class Response {
    final public static function init() {
        // enable output cache
        ob_start();
        // response type
        Request::header('Content-Type', 'text/json; charset=utf-8');
    }

    /**
     * @param array $response
     */
    final public static function json(array $response) {
        self::_text(json_encode($response));
        exit();
    }

    /**
     * @param string $response
     */
    final public static function text($response) {
        echo $response;
        exit();
    }

    /**
     * debug output
     */
    final public static function debug(/* ... */) {
        if (Environment::get_env('debug_mode') === true) {
            call_user_func_array('var_dump', func_get_args());
        }
    }

    /**
     * debug output and exit
     */
    final public static function debug_exit(/* ... */) {
        call_user_func_array(array('self', 'debug'), func_get_args());
        exit();
    }

    /**
     * @param string $response
     */
    final private static function _text($response) {
        echo $response;
    }
}
