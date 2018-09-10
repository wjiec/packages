<?php
/**
 * ActionBase.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Action;


/**
 * Class ActionBase
 * @package WeHub\Action
 */
abstract class ActionBase implements ActionInterface {

    /**
     * @var string
     */
    private $action;

    /**
     * ActionBase constructor.
     * @param string $action
     */
    public function __construct(string $action) {
        $this->action = $action;
    }

    /**
     * @return string
     */
    final public function getActionName(): string {
        return $this->action;
    }

    /**
     * @return string
     */
    final public function getCamelCaseActionName(): string {
        $segments = explode(self::ACTION_SEPARATOR, $this->action);
        if (count($segments) === 1) {
            return $this->action;
        }

        for ($index = 1; $index < count($segments); ++ $index) {
            $segments[$index] = ucfirst($segments[$index]);
        }

        return join('', $segments);
    }

    private const ACTION_SEPARATOR = '_';

}
