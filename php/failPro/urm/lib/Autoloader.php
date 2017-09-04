<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr\Lib;
use Umr\Lib\Exceptions\AutoloadError;


class Autoloader {
    /**
     * @var self
     */
    private static $_loader;

    /**
     * @var string
     */
    private $_root_path;

    /**
     * @var string
     */
    private $_root_namespace;

    /**
     * Autoloader constructor.
     */
    final public function __construct() {
        spl_autoload_register(array('self', 'loader'));
    }

    /**
     * @param string $class_name
     * @return bool
     */
    final public static function loader($class_name) {
        $complete_path = self::_resolve_class_name($class_name);
        // check file exists
        if (!is_file($complete_path)) {
            throw new AutoloadError("class definition not found");
        }

        // include it
        require_once $complete_path;
        return true;
    }

    /**
     * @param string $namespace
     * @param string $path
     */
    final public static function set_root($namespace, $path) {
        if (self::$_loader === null) {
            self::$_loader = new self();

            self::$_loader->_root_path = $path;
            self::$_loader->_root_namespace = $namespace;
        }
    }

    /**
     * @param string $class_name
     * @return bool
     */
    final public static function class_exists($class_name) {
        $complete_path = self::_resolve_class_name($class_name);
        return is_file($complete_path);
    }

    /**
     * @param string $class_name
     * @throws AutoloadError
     * @return string
     */
    final private static function _resolve_class_name($class_name) {
        $segments = explode('\\', $class_name);
        if (empty($segments) || $segments[0] === '') {
            throw new AutoloadError("class name invalid");
        }

        if ($segments[0] !== self::$_loader->_root_namespace) {
            throw new AutoloadError("root namespace invalid");
        }

        // shift root namespace
        array_shift($segments);
        // segment to lower case
        array_map(function(&$segment) {
            $segment = strtolower($segment);
        }, $segments);

        // pop filename
        if (empty($segments)) {
            throw new AutoloadError("class name invalid");
        }
        $filename = array_pop($segments) . '.php';

        // build complete path
        $complete_path = join('/', array(
            self::$_loader->_root_path,
            join('/', $segments),
            $filename
        ));

        return $complete_path;
    }
}
