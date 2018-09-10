<?php
/**
 * MessageInterface.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Message;


use WeHub\Utils\ObjectLikeInterface;


/**
 * Interface MessageInterface
 * @package WeHub\Message
 */
interface MessageInterface extends ObjectLikeInterface {

    /**
     * @return string
     */
    public function getFromUser(): string;

    /**
     * @param string $from_user
     */
    public function setFromUser(string $from_user): void;

    /**
     * @return string
     */
    public function getToUser(): string;

    /**
     * @param string $to_user
     */
    public function setToUser(string $to_user): void;

}
