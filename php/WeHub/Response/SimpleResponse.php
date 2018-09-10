<?php
/**
 * SimpleResponse.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Response;


use WeHub\Action\ActionInterface;

/**
 * Class SimpleResponse
 * @package WeHub\Response
 */
final class SimpleResponse extends ResponseBase {

    /**
     * SimpleResponse constructor.
     * @param ActionInterface $action
     */
    final public function __construct(ActionInterface $action) {
        parent::__construct($action);
    }

}
