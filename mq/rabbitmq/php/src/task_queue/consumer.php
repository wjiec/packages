<?php
/**
 * rabbitmq hello-world
 */
require_once __DIR__ . '/../bootstrap.php';


use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;


$connection = new AMQPStreamConnection(RABBITMQ_MASTER, 5672, 'guest', 'guest');
$channel = $connection->channel();

$channel->queue_declare('task_queue', false, true, false, false);
$channel->basic_qos(null, 1, null);
$channel->basic_consume('task_queue', '', false, false, false, false,
    function(AMQPMessage $message) {
        /* @var \PhpAmqpLib\Channel\AMQPChannel $channel */
        $channel = $message->delivery_info['channel'];

        list($task, $uuid) = explode(':', $message->body);
        echo sprintf("[:] Task uuid: %s\t", $uuid);
        while ($task--) {
            echo ".";
            sleep(1);

            if (random_int(0, 25) === 1) {
                echo "rejected\n";
                return $channel->basic_reject($message->delivery_info['delivery_tag'], true);
            }
        }
        echo "done\n";
        return $channel->basic_ack($message->delivery_info['delivery_tag']);
    }
);

while ($channel->is_consuming()) {
    /** @noinspection PhpUnhandledExceptionInspection */
    $channel->wait();
}

$channel->close();
/** @noinspection PhpUnhandledExceptionInspection */
$connection->close();
