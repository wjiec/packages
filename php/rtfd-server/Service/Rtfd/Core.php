<?php
/**
 * RtfdTools Core Module
 */


/**
 * Class Rtfd_Core
 */
class Rtfd_Core {
    /**
     * Initializing RtfdTools require environment and catch all exceptions/errors
     */
    public static function init_environments() {
        if (function_exists('spl_autoload_register')) {
            spl_autoload_register(array('Rtfd_Core', '__autoload'));
        } else {
            function __autoload($class_name) {
                Rtfd_Core::__autoload($class_name);
            }
        }
        // enable output buffer
        ob_start();
        // catch all exceptions/error
        self::_catch_all_exceptions();
        // include config files
        require_once 'Config/Definition.php';
        // init Database configure
        require_once 'Config/Database.php';
        // init Redis configure
        require_once 'Config/Redis.php';
        // is enable debug mode
        if (_rtfd_debug_mode_ === true) {
            self::enable_debug_mode();
        }
        // initializing Request variables
        Rtfd_Request::init_request();
        // set response default mime-type
        Rtfd_Request::header('Content-Type', 'text/json; charset=utf-8');
        // enable Access-Control-Allow-Origin
        Rtfd_Request::header('Access-Control-Allow-Origin', Rtfd_Request::get_env('http_origin'));
        // enable Access-Control-Allow-Credentials
        Rtfd_Request::header('Access-Control-Allow-Credentials', 'true');
    }

    /**
     * start service
     */
    public static function start_service() {
        // check request method is POST
        if (!in_array(Rtfd_Request::request_method(), self::$ALLOW_METHODS)) {
            // abort current request
            Rtfd_Request::abort(405, array(
                'errno' => '405',
                'error' => 'request method not allowed'
            ));
        }
        // initializing options
        $options = self::_init_options();
        // initializing Config
        $config = new Rtfd_Config($options);
        // initializing Privilege
        $config->set_privilege(self::_init_privilege());
        // check action is allowed
        $config->action_allowed($config->get_option('action'));
        // create Action instance
        $action_class = join('_', array('Rtfd_Action', ucfirst($config->get_option('action'))));
        // check action exists
        if (!Rtfd_Utils::class_exists($action_class)) {
            Rtfd_Request::abort(403, array(
                'errno' => 403,
                'error' => 'action invalid'
            ));
        }
        /* @var Rtfd_Abstract_Action $action_instance */
        $action_instance = new $action_class($config);
        // start action
        $action_instance->start();
        // action miss response
        Rtfd_Response::json_response(array(
            'status' => -1,
            'error_message' => 'Action service missing response'
        ));
    }

    /**
     * enable debug mode
     */
    public static function enable_debug_mode() {
        // response module enable debug
        Rtfd_Response::enable_debug();
        // allow get request
        self::$ALLOW_METHODS[] = 'get';
        // output `Your Enter Debug Mode`
        // echo '<h1>You are in DEBUG mode</h1>';
        // copy $_GET to $_POST
        $_POST = array_merge($_POST, $_GET);
    }

    /**
     * initializing all options and return
     *
     * @return array|mixed|null
     * @throws Exception
     */
    private static function _init_options() {
        // action parameter verify
        $action = Rtfd_Request::post_parameter('act');
        if ($action === null) {
            throw new Rtfd_Exception_ParameterError('parameter `action` not found',
                'Fatal:Rtfd_Core:_init_options');
        }
        // signature parameter verify
        $signature = Rtfd_Request::post_parameter('sig');
        if ($signature === null) {
            throw new Rtfd_Exception_ParameterError('parameter `signature` not found',
                'Fatal:Rtfd_Core:_init_options');
        }
        // timestamp parameter
        $timestamp = Rtfd_Request::post_parameter('ts');
        if ($timestamp === null) {
            throw new Rtfd_Exception_ParameterError('parameter `timestamp` not found',
                'Fatal:Rtfd_Core:_init_options');
        }
        // options
        $options = Rtfd_Request::post_parameter('opts');
        if ($options === null) {
            throw new Rtfd_Exception_ParameterError('parameter `options` not found',
                'Fatal:Rtfd_Core:_init_options');
        } else {
            $options = json_decode($options, true);
            if ($options === null) {
                throw new Rtfd_Exception_ParameterError('parameter `options` invalid',
                    'Fatal:Rtfd_Core:_init_options');
            }
        }
        // return to init
        $options = array_merge(array(
            'action' => $action,
            'timestamp' => $timestamp
        ), $options);
        // verify request
        self::_verify_request($options, $signature);
        // return options;
        return $options;
    }

    /**
     * verify signature and check request expired
     *
     * @param array $options
     * @param string $signature
     * @throws Rtfd_Exception_SignatureError|Rtfd_Exception_FatalError
     */
    private static function _verify_request(array $options, $signature) {
        // verify signature
        if (_rtfd_enable_signature_verify_ && !Rtfd_Utils::verify_signature($signature, $options)) {
            throw new Rtfd_Exception_SignatureError('signature wrong',
                'Fatal:Rtfd_Core:_init_options');
        }
        // verify request expired
        if (_rtfd_enable_expired_verify_
            && _rtfd_request_expired_ > 0
            && $options['timestamp'] + _rtfd_request_expired_ < time()) {
            //-----------------------------------------------------------
            throw new Rtfd_Exception_FatalError('request expired',
                'Fatal:Rtfd_Core:_verify_request');
        }
    }

    /**
     * init user privilege
     *
     * @return string
     */
    private static function _init_privilege() {
        $privilege = Rtfd_Request::get_cookie('rpt');
        if ($privilege === null) {
            return Rtfd_Jwt::generate_token(array(
                'uid' => 0,
                'gid' => 0,
                'username' => 'Guest',
                'expired' => 0,
                'role' => 'Guest'
            ), _rtfd_default_jwt_key_);
        }
        return $privilege;
    }

    /**
     * exceptions/error handler wrapper function
     */
    private static function _catch_all_exceptions() {
        set_exception_handler(array('Rtfd_Core', '_catch_exception_handler'));
        set_error_handler(array('Rtfd_Core', '_catch_error_handler'));
        register_shutdown_function(array('Rtfd_Core', '_at_exit_handler'));
    }

    /**
     * global exceptions handler
     *
     * @Note that providing an explicit Exception type hint for the ex parameter in
     * your callback will cause issues with the changed exception hierarchy in PHP 7.
     *
     * @param Exception|Throwable|Rtfd_Exception_Base $exception
     * @throws Rtfd_Exception_FatalError
     */
    public static function _catch_exception_handler(/* Exception */ /* Throwable */ $exception) {
        if (!($exception instanceof Rtfd_Exception_Base)) {
            throw new Rtfd_Exception_FatalError('cannot catch Exception, please Rtfd_Exception_Xxx', ':Fatal:Rtfd_Core');
        }
        $errno = $exception->get_code();
        $error = $exception->get_message();
        $error_file = $exception->getFile();
        $error_line = $exception->getLine();
        // retransmission to report handler
        self::_report_all_error($errno, $error, $error_file, $error_line, $exception);
    }

    /**
     * handle errors in a script, Note that it is your responsibility to die() if necessary.
     * If the error-handler function returns, script execution will continue with
     * the next statement after the one that caused an error.
     *
     * @param int $errno
     * @param string $error
     * @param string $error_file
     * @param string $error_line
     */
    public static function _catch_error_handler($errno, $error, $error_file, $error_line) {
        // user error level
        $level = array (
            E_WARNING => ':Warning', // 2
            E_PARSE => ':Parse', // 4
            E_NOTICE => ':Notice', // 8
            E_USER_ERROR => 'User:Error', // 256
            E_USER_WARNING => 'User:Waring', // 512
            E_USER_NOTICE => 'User:Notice', // 1024
        );
        // ignore mysqli_connect error
        if (strpos($error, 'mysqli::real_connect') >= 0) {
            return;
        }
        self::_report_all_error($level[$errno], $error, $error_file, $error_line);
        // end script
        exit;
    }

    /**
     * method run at script exit, and catch FATAL error
     */
    public static function _at_exit_handler() {
        $E_FATAL_ERROR = E_CORE_ERROR | E_COMPILE_ERROR | E_RECOVERABLE_ERROR | E_PARSE | /* Fatal Error */ 1;
        $fatal_error = error_get_last();
        // check error type
        if ($fatal_error && $fatal_error['type'] == $fatal_error['type'] & $E_FATAL_ERROR) {
            // clean 'Fatal Error: ...' output
            ob_clean();
            // key information
            $errno = 'Fatal';
            $error = $fatal_error['message'];
            $error_file = $fatal_error['file'];
            $error_line = $fatal_error['line'];
            // report handler
            self::_report_all_error($errno, $error, $error_file, $error_line);
        }
        // no error exit
    }

    /**
     * display error and push to database
     *
     * @TODO database logging
     *
     * @param string|int $errno
     * @param string $error
     * @param string $error_file
     * @param string $error_line
     * @param mixed $extra_data
     */
    private static function _report_all_error($errno, $error, $error_file, $error_line, $extra_data = null) {
        // clean ob cache
        @ob_clean();
        // set http code 500
        Rtfd_Request::set_http_code(400);
        // report error
        Rtfd_Response::json_response(array(
            'error_no' => $errno,
            'error_message' => $error,
            'error_file' => $error_file,
            'error_line' => $error_line,
            'extra_data' => $extra_data
        ));
        // save to database
    }

    /**
     * cannot found `class` called, auto include class definition file.
     *
     * @param string $class_name
     */
    public static function __autoload($class_name) {
        /* no error */
        require_once join('', array(str_replace(array('\\', '_'), '/', $class_name), '.php'));
    }

    /**
     * allowed method of request
     *
     * @var array
     */
    private static $ALLOW_METHODS = array('post');
}
