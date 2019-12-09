<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->exchange_declare('logs_broadcast', 'fanout', false, true, false);

$name = bin2hex(random_bytes(4));
for ($index = 0; $index < 1 << 32; ++$index) {
    $message = json_encode(['producer' => $name, 'index' => $index, 'from' => 'php', 'body' => bin2hex(random_bytes(16))]);
    $channel->basic_publish(new \PhpAmqpLib\Message\AMQPMessage($message), 'logs_broadcast');
    echo sprintf("publish <%s@%s> from php\n", $name, $index);
    sleep(random_int(1, 5));
}
