<?php
/**
 * TaskBase.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Task;


use WeHub\Utils\ObjectLikeInterface;

/**
 * Class TaskBase
 * @package WeHub\Task
 */
abstract class TaskBase implements TaskInterface {

    /**
     * @var string
     */
    private $task_id;

    /**
     * @var array
     */
    private $task_data;

    /**
     * @var int
     */
    private $task_type;

    /**
     * TaskBase constructor.
     * @param int $task_type
     */
    public function __construct(int $task_type) {
        $this->task_id = 1;
        $this->task_data = array();

        $this->task_type = $task_type;
    }

    /**
     * @param ObjectLikeInterface|array $data
     */
    final protected function setData($data) {
        if ($data instanceof ObjectLikeInterface) {
            $this->task_data = $data->toObject();
        } else if (is_array($data)) {
            $this->task_data = $data;
        }
    }

    /**
     * @return array
     */
    public function toObject(): array {
        return array(
            'task_id' => $this->task_id,
            'task_data' => array(
                'task_type' => $this->task_type,
                'task_dict' => $this->task_data
            )
        );
    }

}
