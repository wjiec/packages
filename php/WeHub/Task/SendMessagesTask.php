<?php
/**
 * SendMessagesTask.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Task;


use WeHub\Message\MessageInterface;

/**
 * Class SendMessagesTask
 * @package WeHub\Task
 */
class SendMessagesTask extends TaskBase {

    /**
     * @var MessageInterface[]
     */
    private $messages;

    /**
     * @var string
     */
    private $from_user;

    /**
     * @var string
     */
    private $to_user;

    /**
     * SendMessagesTask constructor.
     * @param string $from_user
     * @param string $to_user
     */
    final public function __construct(string $from_user, string $to_user) {
        parent::__construct(TaskTypes::SEND_MESSAGES_TYPE);

        $this->messages = array();
        $this->from_user = $from_user;
        $this->to_user = $to_user;
    }

    /**
     * @param MessageInterface $message
     */
    final public function addMessage(MessageInterface $message) {
        $message->setFromUser($this->from_user);
        $message->setToUser($this->to_user);

        $this->messages[] = $message;
    }

    /**
     * @return array
     */
    final public function toObject(): array {
        $message_list = array();
        foreach ($this->messages as $message) {
            $message_list[] = $message->toObject();
        }
        $this->setData(array(
            'room_wxid' => $this->from_user,
            'wxid' => $this->to_user,
            'msg_list' => $message_list
        ));

        return parent::toObject();
    }

}
