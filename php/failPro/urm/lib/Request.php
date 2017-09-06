<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr\Lib;


class Request {
    /**
     * @var self
     */
    private static $_request;

    /**
     * @var array
     */
    private $_get_params;

    /**
     * @var array
     */
    private $_post_params;

    /**
     * @var string
     */
    private $_request_body;

    /**
     * Request constructor.
     */
    final public function __construct() {
        $this->_get_params = $_GET;
        $this->_post_params = $_POST;

        if (self::request_method() !== 'GET') {
            $this->_request_body = file_get_contents('php://input');
        }
    }

    /**
     * initializing request environment
     */
    final public static function init() {
        self::$_request = new self();
    }

    /**
     * @return string
     */
    final public static function request_method() {
        return Environment::get_server_env('request_method');
    }

    /**
     * @param bool $json
     * @return string|array
     */
    final public static function request_body($json = true) {
        if ($json === true) {
            return json_decode(self::$_request->_request_body, true);
        }
        return self::$_request->_request_body;
    }

    /**
     * @param string $key
     * @param string $value
     */
    final public static function header($key, $value) {
        header("{$key}: {$value}");
    }
}
