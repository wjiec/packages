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
namespace Restful;
use Phalcon\Db\Adapter\Pdo\Mysql;
use Phalcon\Di\FactoryDefault;
use Phalcon\Http\Response;
use Phalcon\Loader;
use Phalcon\Mvc\Micro;

// create loader
$loader = new Loader();

// models loader
$loader->registerDirs(array(
    __DIR__ . '/models'
))->register();

// create dependency management
$di = new FactoryDefault();

// database service
$di->set('db', function() {
    return new Mysql(array(
        'host' => '192.168.199.124',
        'username' => 'root',
        'password' => 'root',
        'dbname' => 'phalcon'
    ));
});

// create micro application
$micro = new Micro($di);

// fetch all users
$micro->get('/api/users', function() use ($micro) {
    $phql = 'select * from Users order by name';
    $users = $micro->modelsManager->executeQuery($phql);

    $response = array();
    foreach ($users as $user) {
        $response[] = array(
            'id' => $user->id,
            'name' => $user->name,
            'password' => $user->password
        );
    }

    echo json_encode($response);
});

// search an user base on some parameters
$micro->get('/api/user', function() use ($micro) {
    if (!$micro->request->hasQuery('name')) {
        return $micro->response->setStatusCode(400);
    }

    $phql = 'select * from Users where name like :name: order by name';
    $users = $micro->modelsManager->executeQuery($phql, array(
        'name' => "%{$micro->request->getQuery('name')}%"
    ));

    $response = array();
    foreach ($users as $user) {
        $response[] = array(
            'id' => $user->id,
            'name' => $user->name,
            'password' => $user->password
        );
    }

    echo json_encode($response);
});

// search an user base on primary key
$micro->get('/api/user/{id:\d+}', function(int $id) use ($micro) {
    $phql = 'select * from Users where id=:id:';
    $user = $micro->modelsManager->executeQuery($phql, array(
        'id' => $id
    ))->getFirst();

    $response = new Response();
    if ($user === false) {
        $response->setJsonContent(array(
            'code' => -1,
            'message' => 'not found any user'
        ));
    } else {
        $response->setJsonContent(array(
            'code' => 0,
            'message' => 'success',
            'data' => array(
                'id' => $user->id,
                'name' => $user->name,
                'password' => $user->password
            )
        ));
    }

    return $response;
});

// add new user
$micro->post('/api/user', function() use ($micro) {
    $user = $micro->request->getJsonRawBody();
    $phql = 'insert into Users (name, password) values (:name:, :password:)';

    $status = $micro->modelsManager->executeQuery($phql, array(
        'name' => $user->name,
        'password' => $user->password
    ));

    $response = new Response();
    if ($status->success()) {
        $response->setStatusCode(201, 'Created');

        $user->id = $status->getModel()->id;
        $response->setJsonContent(array(
            'code' => 0,
            'message' => 'success',
            'user' => $user
        ));
    } else {
        $response->setStatusCode(409, 'Conflict');

        $errors = array();
        foreach ($status->getMessages() as $message) {
            $errors[] = $message->getMessage();
        }

        $response->setJsonContent(array(
            'code' => -1,
            'messages' => $errors
        ));
    }

    return $response;
});

// update an user
$micro->put('/api/user/{id:\d+}', function(int $id) use ($micro) {
    $user = $micro->request->getJsonRawBody();
    $phql = 'update Users set name = :name:, password = :password: where id = :id:';

    $status = $micro->modelsManager->executeQuery($phql, array(
        'id' => $id,
        'name' => $user->name,
        'password' => $user->password
    ));

    $response = new Response();
    if ($status->success()) {
        $response->setJsonContent(array(
            'code' => 0,
            'message' => 'success'
        ));
    } else {
        $response->setStatusCode(409, 'Conflict');

        $errors = array();
        foreach ($status->getMessages() as $message) {
            $errors[] = $message->getMessage();
        }

        $response->setJsonContent(array(
            'code' => -1,
            'messages' => $errors
        ));
    }

    return $response;
});

// delete an user
$micro->delete('/api/user/{id:\d+}', function(int $id) use ($micro) {
    $phql = 'delete Users where id = :id:';

    $status = $micro->modelsManager->executeQuery($phql, array(
        'id' => $id
    ));

    $response = new Response();
    if ($status->success()) {
        $response->setJsonContent(array(
            'code' => 0,
            'message' => 'success'
        ));
    } else {
        $response->setStatusCode(409, 'Conflict');

        $errors = array();
        foreach ($status->getMessages() as $message) {
            $errors[] = $message->getMessage();
        }

        $response->setJsonContent(array(
            'code' => -1,
            'messages' => $errors
        ));
    }

    return $response;
});

// delete all users
$micro->delete('/api/users', function() use ($micro) {
    $phql = 'delete Users';

    $status = $micro->modelsManager->executeQuery($phql);

    $response = new Response();
    if ($status->success()) {
        $response->setJsonContent(array(
            'code' => 0,
            'message' => 'success'
        ));
    } else {
        $response->setStatusCode(409, 'Conflict');

        $errors = array();
        foreach ($status->getMessages() as $message) {
            $errors[] = $message->getMessage();
        }

        $response->setJsonContent(array(
            'code' => -1,
            'messages' => $errors
        ));
    }

    return $response;
});

// create request environment and route request
try {
    $micro->handle();
} catch (\Exception $e) {
    echo json_encode(array('code' => 404, 'message' => $e->getMessage()));
}
