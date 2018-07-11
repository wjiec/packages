<?php
/**
 * index.php
 *
 * @package   phalcon
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 * @link      https://github.com/JShadowMan/here
 */
declare(strict_types=1);

// namespace definition
namespace Basic;
use Phalcon\Db\Adapter\Pdo\Mysql;
use Phalcon\Di\FactoryDefault;
use Phalcon\Http\Response;
use Phalcon\Loader;
use Phalcon\Mvc\Application;
use Phalcon\Mvc\Url;
use Phalcon\Mvc\View;


// document root
define('DOCUMENT_ROOT', dirname(__DIR__));

// application root
define('APPLICATION_ROOT', DOCUMENT_ROOT . '/application');

// loader register
$loader = new Loader();

// component of controllers and models
$loader->registerDirs(array(
    APPLICATION_ROOT . '/controllers',
    APPLICATION_ROOT . '/models'
))->register();

// create dependency management
$di = new FactoryDefault();

// view service
$di->set('view', function() {
    $view = new View();

    $view->setViewsDir(APPLICATION_ROOT . '/views');
    return $view;
});

// setup base url
$di->set('url', function() {
    $url = new Url();

    $url->setBaseUri('/');
    return $url;
});

// database service
$di->set('db', function() {
    $mysql = new Mysql(array(
        // on my raspberry pi 3B+
        'host' => '192.168.199.124',
        'username' => 'root',
        'password' => 'root',
        'dbname' => 'phalcon'
    ));

    return $mysql;
});

// initializing request environment, route the incoming
// request and dispatch any discovered actions
$application = new Application($di);
try {
    $response = $application->handle();
    $response->send();
} catch (\Exception $e) {
    (new Response())
        ->setStatusCode(500)
        ->setJsonContent(array('code' => 500, 'message' => $e->getMessage()))
        ->send();
}
