<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;


$connection = new AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->queue_declare('hello', false, false, false, false);
while (true) {
    $channel->basic_publish(new AMQPMessage('hello world from php'), '', 'hello');
}

$channel->close();
/** @noinspection PhpUnhandledExceptionInspection */
$connection->close();
