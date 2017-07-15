<?php
/**
 * Response Module of
 */


class Rtfd_Response {
    /**
     *  make json response
     *
     * @param array $response
     * @throws Exception
     */
    public static function json_response(array $response) {
        // json encode, convert to string
        $text = json_encode($response);
        // convert fail
        if ($text === null) {
            throw new Rtfd_Exception_FatalError("convert json_response to string fail");
        }
        // output
        self::_make_response($text);
    }

    /**
     * simple response wrapper for json_response
     *
     * @param bool $status
     * @param mixed|null $data
     */
    public static function simple_response($status, $data) {
        self::json_response(array(
            'status' => boolval($status) ? 0 : 1,
            'data' => $data
        ));
    }

    /**
     * output plain text to client
     *
     * @param $text
     */
    public static function plain_response($text) {
        // convert to string
        $text = Rtfd_Utils::to_string($text);
        // output
        self::_make_response($text);
    }

    /**
     * using debug function output parameters, and exit
     */
    public static function debug_output(/* ... */) {
        // is not debug mode, return
        if (!self::$_debug_mode) {
            return ;
        }
        // set response content type
        Rtfd_Request::header('Content-Type', 'text/html; charset=utf-8');
        // get args with parameters
        $args = func_get_args();
        if (empty($args)) {
            throw new Rtfd_Exception_FatalError('empty parameters', 'Gm:Response:debug_output');
        }
        // display all args
        if (self::$_debug_function === 'var_dump') {
            // var_dump(..., ..., ....)
            call_user_func_array(self::$_debug_function, $args);
        } else {
            foreach ($args as $arg) {
                // print_r(...)
                // ...
                call_user_func(self::$_debug_function, $arg);
            }
        }
        // exit
        exit;
    }

    /**
     * make response and return to client
     *
     * @param string $text_response
     */
    private static function _make_response($text_response) {
        // check debug mode is disable
        if (self::$_debug_mode === false) {
            ob_clean();
        }
        // echo response and exit
        echo $text_response;
        exit;
    }

    /**
     * enable debug mode
     */
    public static function enable_debug() {
        self::$_debug_mode = true;
    }

    /**
     * change debug output function
     */
    public static function using_var_dump() {
        self::$_debug_function = 'var_dump';
    }

    /**
     * change debug output function
     */
    public static function using_print_r() {
        self::$_debug_function = 'print_r';
    }

    /**
     * change debug output function
     */
    public static function using_var_export() {
        self::$_debug_function = 'var_export';
    }

    /**
     * debug flag
     *
     * @var bool
     */
    private static $_debug_mode = false;

    /**
     * debug output function name
     *  * var_dump
     *  * print_r
     *  * var_export
     *
     * @var string
     */
    private static $_debug_function = 'var_dump';
}
