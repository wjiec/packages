<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr\Lib;
use Umr\Lib\Exceptions\EnvironmentError;


class Environment {
    /**
     * @var array
     */
    private static $_envs = array();

  /**
   * @var array
   */
    private static $_server_envs;

    /**
     * initializing environment
     */
    final static function init() {
        // Request initializing
        Request::init();
        // response initializing
        Response::init();
    }

    /**
     * @param string $name
     * @param string $value
     * @param bool $override
     */
    final static function set_env($name, $value, $override = true) {
        if ($override === false) {
            if (array_key_exists($name, self::$_envs)) {
                throw new EnvironmentError("environment key exists");
            }
        }

        self::$_envs[$name] = $value;
    }

    /**
     * @param string $name
     * @param null $default
     */
    final static function get_env($name, $default = null) {
        if (array_key_exists($name, self::$_envs)) {
            return self::$_envs[$name];
        }
        return $default;
    }

  /**
   * @param string $name
   * @param null $default
   */
    final static function get_server_env($name, $default = null) {
        if (self::$_server_envs === null) {
            foreach ($_SERVER as $key => $val) {
                self::$_server_envs[strtolower($key)] = $val;
            }
        }

        $name = strtolower($name);
        if (array_key_exists($name, self::$_server_envs)) {
            return self::$_server_envs[$name];
        }
        return $default;
    }
}
