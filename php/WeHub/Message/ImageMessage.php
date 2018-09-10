<?php
/**
 * ImageMessage.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Message;


/**
 * Class ImageMessage
 * @package WeHub\Message
 */
final class ImageMessage extends MessageBase {

    /**
     * ImageMessage constructor.
     * @param string $image_url
     */
    final public function __construct(string $image_url) {
        parent::__construct(MessageTypes::IMAGE_MESSAGE);

        $this->setExtraData(array(
            'msg' => $image_url
        ));
    }

}
