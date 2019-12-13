<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->exchange_declare('logs_routing', 'direct', false, true, false);
$channel->queue_declare('logs_routing_saver', false, true, false, true);
$channel->queue_bind('logs_routing_saver', 'logs_routing', 'warning');
$channel->queue_bind('logs_routing_saver', 'logs_routing', 'error');

$channel->basic_qos(null, 1, true);
$channel->basic_consume('logs_routing_saver', null, false, false, false, false,
    function(\PhpAmqpLib\Message\AMQPMessage $message) use ($channel) {
        $data = json_decode($message->body, true);
        file_put_contents('routing.log', sprintf("%-12s %-12s %-8s %-12s: %s\n", $data['from'], $data['producer'], $data['index'],
            $message->delivery_info['routing_key'], $data['body']), FILE_APPEND);
        $channel->basic_ack($message->getDeliveryTag());
    }
);

while ($channel->is_consuming()) {
    $channel->wait();
}
