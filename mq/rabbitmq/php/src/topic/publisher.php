<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->exchange_declare('logs_topic', 'topic', false, true, false);

$producer = bin2hex(random_bytes(4));
$levels = ['debug', 'info', 'warning', 'error'];
$modules = ['account', 'base', 'statistics', 'open'];
for ($index = 0; $index < 10000; ++$index) {
    $channel->basic_publish(new \PhpAmqpLib\Message\AMQPMessage(json_encode([
        'from' => 'php',
        'producer' => $producer,
        'index' => $index,
        'body' => bin2hex(random_bytes(16))
    ])), 'logs_topic', $modules[random_int(0, 3)] . '. ' . $levels[random_int(0, 3)]);
    sleep(random_int(1, 2));
}
