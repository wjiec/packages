<?php
/**
 * PullTaskResponse.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Response;


use WeHub\Action\ActionInterface;
use WeHub\Task\TaskInterface;


/**
 * Class PullTaskResponse
 * @package WeHub\Response
 */
final class PullTaskResponse extends ResponseBase {

    /**
     * PullTaskResponse constructor.
     * @param ActionInterface $action
     * @param TaskInterface $task
     */
    final public function __construct(ActionInterface $action, TaskInterface $task) {
        parent::__construct($action);

        $this->set_data($task->toObject());
    }

}
