<?php

// debug mode flag
define('_rtfd_debug_mode_', defined('DEBUG_MODE'));
// is enable signature verify
define('_rtfd_enable_signature_verify_', !defined('SKIP_SIGNATURE'));
// is enable request expired verify
define('_rtfd_enable_expired_verify_', !defined('SKIP_REQUEST_EXPIRED'));
// signature key
define('_rtfd_signature_key_', 'ReadTheFuckDocs');
// request expired
define('_rtfd_request_expired_', 60);
// http request timeout
define('_rtfd_http_request_timeout_', 1);
// database connecting timeout
define('_rtfd_database_connecting_timeout_', 1);
// redis connecting timeout
define('_rtfd_redis_connecting_timeout_', 0);
// default jwt algorithm
define('_rtfd_default_jwt_alg_', 'HS256');
// default jwt key
define('_rtfd_default_jwt_key_', 'ReadTheFuckDocs');
