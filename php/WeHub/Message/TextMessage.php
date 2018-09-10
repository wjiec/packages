<?php
/**
 * TextMessage.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Message;


/**
 * Class TextMessage
 * @package WeHub\Message
 */
final class TextMessage extends MessageBase {

    /**
     * TextMessage constructor.
     * @param string $message
     */
    final public function __construct(string $message) {
        parent::__construct(MessageTypes::TEXT_MESSAGE);

        $this->setExtraData(array(
            'msg' => $message
        ));
    }

}
