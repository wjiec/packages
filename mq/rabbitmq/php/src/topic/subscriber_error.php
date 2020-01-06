<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->exchange_declare('logs_topic', 'topic', false, true, false);
$channel->queue_declare('logs_topic_error', false, false, false, true);
$channel->queue_bind('logs_topic_error', 'logs_topic', '*.error');

$channel->basic_qos(null, 1, true);
$channel->basic_consume('logs_topic_error', null, false, false, false, false,
    function(\PhpAmqpLib\Message\AMQPMessage $message) use ($channel) {
        $data = json_decode($message->body, true);
        echo sprintf("%-12s %-12s %-8s %-20s: %s\n", $data['from'], $data['producer'], $data['index'],
            $message->delivery_info['routing_key'], $data['body']);

        if (random_int(0, 20) === 1) {
            $channel->basic_reject($message->getDeliveryTag(), true);
            return;
        }
        $channel->basic_ack($message->getDeliveryTag());
    }
);

while ($channel->is_consuming()) {
    $channel->wait();
}
