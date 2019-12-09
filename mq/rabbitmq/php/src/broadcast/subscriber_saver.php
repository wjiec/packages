<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->queue_declare('logs_broadcast_saver', false, true, false, true);
$channel->queue_bind('logs_broadcast_saver', 'logs_broadcast', 'logs_broadcast_saver');

$channel->basic_consume('logs_broadcast_saver', null, false, false, false, false,
    function(\PhpAmqpLib\Message\AMQPMessage $message) {
        $data = json_decode($message->body);
        file_put_contents('broadcast.log', sprintf("<%s:%s@%s>: %s\n", $data->from, $data->producer, $data->index, $data->body), FILE_APPEND);

        /* @var \PhpAmqpLib\Channel\AMQPChannel $channel */
        $channel = $message->delivery_info['channel'];
        return $channel->basic_ack($message->getDeliveryTag());
    }
);

while ($channel->is_consuming()) {
    $channel->wait();
}
