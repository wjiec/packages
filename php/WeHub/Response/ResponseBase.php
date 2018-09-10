<?php
/**
 * ResponseBase.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Response;


use WeHub\Action\ActionInterface;


/**
 * Class ResponseBase
 * @package WeHub\Response
 */
abstract class ResponseBase implements ResponseInterface {

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
    public function __construct(ActionInterface $action) {
        $this->action = $action;
        $this->error_code = 0;
        $this->error_reason = '';
        $this->data = array();
    }

    /**
     * @param int $code
     * @param string $reason
     */
    public function set_error(int $code, string $reason): void {
        $this->error_code = $code;
        $this->error_reason = $reason;
    }

    /**
     * @param array $data
     */
    final public function set_data(array $data) {
        $this->data = $data;
    }

    /**
     * @return array
     */
    final public function toObject(): array {
        return array(
            'error_code' => $this->error_code,
            'error_reason' => $this->error_reason,
            'ack_type' => sprintf('%s%s', $this->action->getActionName(), self::ACTION_CHECK_SUFFIX),
            'data' => $this->data
        );
    }

    private const ACTION_CHECK_SUFFIX = '_ack';

}
