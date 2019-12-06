<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


use PhpAmqpLib\Connection\AMQPStreamConnection;


$connection = new AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->queue_declare('task_queue', false, true, false, false);
while (true) {
    $body = sprintf('%s:%s@php', random_int(1, 5), \Ramsey\Uuid\Uuid::uuid4());
    $channel->basic_publish(new \PhpAmqpLib\Message\AMQPMessage($body, ['delivery_mode' => 2]), '', 'task_queue');
    echo sprintf("[*] Publish message %s to mq by php\n", $body);

    sleep(random_int(1, 2));
}

