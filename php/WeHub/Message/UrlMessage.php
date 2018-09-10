<?php
/**
 * UrlMessage.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Message;


/**
 * Class UrlMessage
 * @package WeHub\Message
 */
final class UrlMessage extends MessageBase {

    /**
     * UrlMessage constructor.
     * @param string $url
     * @param string $title
     * @param string $sub_title
     * @param string $image_url
     */
    final public function __construct(string $url, string $title, string $sub_title, string $image_url) {
        parent::__construct(MessageTypes::URL_MESSAGE);

        $this->setExtraData(array(
            'link_url' => $url,
            'link_title' => $title,
            'link_desc' => $sub_title,
            'link_img_url' => $image_url
        ));
    }

}
