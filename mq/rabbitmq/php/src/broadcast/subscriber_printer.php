<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


$connection = new \PhpAmqpLib\Connection\AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->queue_declare('logs_broadcast_printer', false, true, false, true);
$channel->queue_bind('logs_broadcast_printer', 'logs_broadcast', 'logs_broadcast_printer');

$channel->basic_consume('logs_broadcast_printer', null, false, false, false, false,
    function(\PhpAmqpLib\Message\AMQPMessage $message) {
        $data = json_decode($message->body);
        echo sprintf("retrieve message <%s:%s@%s> %s\n", $data->from, $data->producer, $data->index, $data->body);

        /* @var \PhpAmqpLib\Channel\AMQPChannel $channel */
        $channel = $message->delivery_info['channel'];
        for ($index = 0; $index < random_int(1, 3); ++$index) {
            echo '.';
            sleep(1);
            if (random_int(1, 25) === 1) {
                echo "reject\n";
                return $channel->basic_reject($message->getDeliveryTag(), true);
            }
        }
        echo "done\n";
        return $channel->basic_ack($message->getDeliveryTag());
    }
);

while ($channel->is_consuming()) {
    $channel->wait();
}
