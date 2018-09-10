<?php
/**
 * MessageBase.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Message;


/**
 * Class MessageBase
 * @package WeHub\Message
 */
abstract class MessageBase implements MessageInterface {

    /**
     * @var int
     */
    private $message_type;

    /**
     * @var string
     */
    private $from_user;

    /**
     * @var string
     */
    private $to_user;

    /**
     * @var array
     */
    private $extra_data;

    /**
     * MessageBase constructor.
     * @param int $message_type
     * @param string $from_user
     * @param string $to_user
     */
    public function __construct(int $message_type, ?string $from_user = null, ?string $to_user = null) {
        $this->message_type = $message_type;
        $this->from_user = $from_user;
        $this->to_user = $to_user;
        $this->extra_data = array();
    }

    /**
     * @return string
     */
    final public function getFromUser(): string {
        return $this->from_user;
    }

    /**
     * @param string $from_user
     */
    final public function setFromUser(string $from_user): void {
        $this->from_user = $from_user;
    }

    /**
     * @return string
     */
    final public function getToUser(): string {
        return $this->to_user;
    }

    /**
     * @param string $to_user
     */
    final public function setToUser(string $to_user): void {
        $this->to_user = $to_user;
    }

    /**
     * @param array $data
     */
    final public function setExtraData(array $data): void {
        $this->extra_data = $data;
    }

    /**
     * @return array
     */
    final public function toObject(): array {
        return array_merge(array(
            'msg_type' => $this->message_type,
            'room_wxid' => $this->from_user,
            'wxid' => $this->to_user
        ), $this->extra_data);
    }

}
