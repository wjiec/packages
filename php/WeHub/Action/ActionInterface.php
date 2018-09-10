<?php
/**
 * ActionInterface.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Action;


/**
 * Interface ActionInterface
 * @package WeHub\Action
 */
interface ActionInterface {

    /**
     * ActionInterface constructor.
     * @param string $action
     */
    public function __construct(string $action);

    /**
     * @return string
     */
    public function getActionName(): string;

    /**
     * @return string
     */
    public function getCamelCaseActionName(): string;

}
