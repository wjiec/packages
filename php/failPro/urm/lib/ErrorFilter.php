<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr\Lib;
use Umr\Lib\Abstracts\ExceptionBase;


class ErrorFilter {
    /**
     * ErrorFilter constructor.
     */
    final public function __construct() {
        set_exception_handler(array('Umr\\Lib\\ErrorFilter', 'exception_handler'));
    }

    /**
     * filter all exception and error
     */
    final public static function init() {
        new self();
    }

    /**
     * @param ExceptionBase $exception
     */
    final public static function exception_handler(ExceptionBase $exception) {
        $errno = $exception->getCode();
        $error = $exception->getMessage();
        $error_file = $exception->getFile();
        $error_line = $exception->getLine();

        self::_report_error($errno, $error, $error_file, $error_line, $exception);
    }

    /**
     * @param int $errno
     * @param string $error
     * @param string $error_file
     * @param string $error_line
     * @param null|mixed $extra
     */
    final public static function _report_error($errno, $error, $error_file, $error_line, $extra = null) {
        if (Environment::get_env('debug_mode') === true) {
            Response::json(array(
                'errno' => $errno,
                'error' => $error,
                'error_file' => $error_file,
                'error_line' => $error_line,
                'error_extra' => htmlentities($extra)
            ));
        }
        Response::json(array(
            'errno' => $errno,
            'error' => $error
        ));
    }
}
