<?php
/**
 * Response.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub;


use WeHub\Action\ActionInterface;

/**
 * Class Response
 * @package WeHub
 */
final class Response {

    /**
     * @var int
     */
    private $error_code;

    /**
     * @var string
     */
    private $error_reason;

    /**
     * @var ActionInterface
     */
    private $action;

    /**
     * @var array
     */
    private $data;

    /**
     * Response constructor.
     * @param ActionInterface $action
     */
    final public function __construct(ActionInterface $action) {
        $this->action = $action;
    }

    /**
     * @return array
     */
    final public function to_object(): array {
        return array();
    }

}
