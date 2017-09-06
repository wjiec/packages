<?php
/**
 * @package UMR
 * @author Wang
 */
namespace Umr;
use Umr\Lib\Request;
use Umr\Lib\Response;
use Umr\Lib\Autoloader;
use Umr\Lib\Environment;
use Umr\Lib\ErrorFilter;

/* initializing Autoloader */
require_once 'lib/Autoloader.php';

/* set root namespace/path */
Autoloader::set_root('Umr', dirname(__FILE__));

/* initializing environment */
Environment::init();
Environment::set_env('debug_mode', true);

/* global error handler */
ErrorFilter::init();

/* router init */
Response::debug(Request::request_body(false));
