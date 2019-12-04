<?php
/**
 * rabbitmq hello-world
 */

use PhpAmqpLib\Message\AMQPMessage;

require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->queue_declare('hello', false, false, false, false);


$consumer = function(AMQPMessage $message) {
    echo $message->getBody() . PHP_EOL;
};
$channel->basic_consume('hello', '', false, true,false, false, $consumer);

while ($channel->is_consuming()) {
    /** @noinspection PhpUnhandledExceptionInspection */
    $channel->wait();
}
