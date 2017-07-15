<?php
/**
 * Request Module
 */

class Rtfd_Request {
    /**
     * url prefix
     *
     * @var string
     */
    private static $_url_prefix = null;

    /**
     * server variable
     *
     * @var array
     */
    private static $_server = array();

    /**
     * GET parameters
     *
     * @var array
     */
    private static $_get_parameters = array();

    /**
     * POST parameters
     *
     * @var array
     */
    private static $_post_parameters = array();

    /**
     * COOKIE parameters
     *
     * @var array
     */
    private static $_cookie_parameters = array();

    /**
     * request body contents
     *
     * @var string
     */
    private static $_request_contents;

    /** Here_Request constructor
     *
     * @throws Exception
     */
    public function __construct() {
        // server variables
        foreach ($_SERVER as $key => $value) {
            self::$_server[strtolower($key)] = $value;
        }
        // url parameters
        self::$_get_parameters = $_GET;
        // post parameters
        self::$_post_parameters = $_POST;
        // cookie parameters
        self::$_cookie_parameters = $_COOKIE;
        // request body contents
        self::$_request_contents = file_get_contents('php://input');
    }

    /*
     * current request method, [lower case]
     */
    public static function request_method() {
        return strtolower(self::get_env('request_method'));
    }

    /**
     * current request path
     *
     * @return array|null|string
     */
    public static function request_url() {
        return self::get_env('request_uri');
    }

    /**
     * from $_SERVER getting value
     *
     * @param string $key
     * @param mixed $default
     * @return string|null|array
     */
    public static function get_env($key = null, $default = null) {
        // return all server variable
        if ($key === null) {
            return self::$_server;
        }
        // get single item
        $key = strtolower($key);
        if (array_key_exists($key, self::$_server)) {
            return self::$_server[$key];
        }
        return $default;
    }

    /**
     * from GET method getting parameter
     *
     * @param string|null $key
     * @param mixed $default
     * @return array|mixed|null
     */
    public static function get_parameter($key = null, $default = null) {
        if ($key == null) {
            return self::$_get_parameters;
        } else if (array_key_exists($key, self::$_get_parameters)) {
            return self::$_get_parameters[$key];
        }
        return $default;
    }

    /**
     * wrapper function of `get_parameter`
     *
     * @param string|null $key
     * @param mixed $default
     * @return array|mixed|null
     */
    public static function url_parameter($key = null, $default = null) {
        return self::get_parameter($key, $default);
    }

    /**
     * POST method request form data or parameters
     *
     * @param string|null $key
     * @param mixed $default
     * @return array|mixed|null
     */
    public static function post_parameter($key = null, $default = null) {
        if ($key == null) {
            return self::$_post_parameters;
        } else if (array_key_exists($key, self::$_post_parameters)) {
            return self::$_post_parameters[$key];
        }
        return $default;
    }

    /**
     * both GET and POST parameters
     *
     * @param string|null $key
     * @param mixed $default
     * @return array|mixed|null
     */
    public static function request_parameter($key = null, $default = null) {
        $get_parameter = self::get_parameter($key, $default);
        $post_parameter = self::post_parameter($key, $default);
        // check post parameters first
        if ($key != null && $post_parameter !== $default) {
            return $post_parameter;
        } else if ($key != null && $get_parameter !== $default) {
            return $get_parameter;
        }
        // both GET parameters and post parameters
        $request_parameters = array_merge(self::$_get_parameters, self::$_post_parameters);
        return $request_parameters;
    }

    /**
     * @param string $key
     * @param mixed $default
     * @return mixed|null
     */
    public static function get_cookie($key, $default = null) {
        if (array_key_exists($key, self::$_cookie_parameters)) {
            return self::$_cookie_parameters[$key];
        }
        return $default;
    }

    /**
     * setting cookie to client
     *
     * @param string $key
     * @param string $value
     * @param int $expired
     */
    public static function set_cookie($key, $value, $expired = 0) {
        // http-only cookie
        if (!setcookie($key, Rtfd_Utils::to_string($value), $expired == 0 ? 0 : time() + $expired,
            '/', '', false, true)) {
            //-----------------------------------------------
            Rtfd_Request::abort(500, array(
                'errno' => 500,
                'error' => 'cannot setting cookie'
            ));
        }
        self::$_cookie_parameters[$key] = Rtfd_Utils::to_string($value);
    }

    /**
     * POST request body, if $json set true, than will decode contents to Json format
     *
     * @param bool $json
     * @param bool $assoc
     * @return string|array
     */
    public static function get_request_contents($json = false, $assoc = true) {
        if ($json == true) {
            return json_decode(self::$_request_contents, $assoc);
        }
        return self::$_request_contents;
    }

    /**
     * redirection to new url
     *
     * @param string $url
     */
    public static function redirection($url) {
        // clean output buffer
        ob_clean();
        // redirection to new address
        self::header('Location', $url);
        // no output
        exit();
    }

    /**
     * set http error code
     *
     * @param int $code
     */
    public static function set_http_code($code) {
        header('X-Response-Status: ' . $code, null, $code);
    }

    /**
     * abort current request
     *
     * @param int $code
     * @param array $response
     */
    public static function abort($code, array $response) {
        // set http code
        self::set_http_code($code);
        // make response
        Rtfd_Response::json_response($response);
    }

    /**
     * set http header
     *
     * @param string $key
     * @param string $value
     */
    public static function header($key, $value) {
        header(join(': ', array($key, $value)));
    }

    /**
     * check current connection is safe
     *
     * @return bool
     */
    public static function is_secure() {
        return (
            (self::get_env('https') && strtolower(self::get_env('https')) != 'off') ||
            (self::get_env('server_port') == '443')
        );
    }

    /**
     * get current web server prefix
     *
     * @return string
     */
    public static function get_url_prefix() {
        if (self::$_url_prefix == null) {
            self::$_url_prefix = join('', array(
                // http protocol
                self::is_secure() ? 'https' : 'http',
                // domain separator
                '://',
                // domain
                self::get_env('http_host') ?: self::get_env('server_name'),
                // if using non default port
                !in_array(self::get_env('server_port'), array(80, 443)) ? ':' : '',
                // server port
                !in_array(self::get_env('server_port'), array(80, 443)) ? self::get_env('server_port') : '',
            ));
        }
        return self::$_url_prefix;
    }

    /**
     * getting complete url
     *
     * @param string $url
     * @return string
     */
    public static function url_completion($url) {
        return self::get_url_prefix() . (($url[0] == '/') ? $url : ('/' . $url));
    }

    /**
     * get request headers
     *
     * @return array|false
     */
    public static function get_request_headers() {
        if (!function_exists('apache_request_headers')) {
            function apache_request_headers() {
                $headers = array();
                $http_reg = '/\AHTTP_/';
                foreach($_SERVER as $key => $val) {
                    if(preg_match($http_reg, $key)) {
                        $arh_key = preg_replace($http_reg, '', $key);
                        $rx_matches = explode('_', $arh_key);
                        if(count($rx_matches) > 0 and strlen($arh_key) > 2) {
                            foreach($rx_matches as $ak_key => $ak_val) {
                                $rx_matches[$ak_key] = ucfirst($ak_val);
                            }
                            $arh_key = implode('-', $rx_matches);
                        }
                        $headers[$arh_key] = $val;
                    }
                }
                return($headers);
            }
        }
        return apache_request_headers();
    }

    /**

     * get remote/client ip address
     *
     * @return null|string
     */
    public static function get_remote_ip() {
        return self::get_env('remote_addr');
    }

    /**
     * send http request by POST
     *
     * @param string $url
     * @param array $url_params
     * @param string $data
     * @return string|bool
     */
    public static function http_post($url, array $url_params = array(), $data = null) {
        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, empty($url_params) ? $url : ($url . '?' . http_build_query($url_params)));
        curl_setopt($ch, CURLOPT_TIMEOUT, _rtfd_http_request_timeout_);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
        curl_setopt($ch, CURLOPT_POST, true);

        $result = curl_exec($ch);
        curl_close($ch);

        return $result;
    }

    /**
     * send http request by GET
     *
     * @param $url
     * @param array $url_params
     * @return string|bool
     */
    public static function http_get($url, array $url_params = array()) {
        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, empty($url_params) ? $url : ($url . '?' . http_build_query($url_params)));
        curl_setopt($ch, CURLOPT_TIMEOUT, _rtfd_http_request_timeout_);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HEADER, false);

        $result = curl_exec($ch);
        curl_close($ch);

        return $result;
    }

    /**
     * initializing all server parameters and environment
     *
     * @return Rtfd_Request
     */
    public static function init_request() {
        return new Rtfd_Request();
    }
}