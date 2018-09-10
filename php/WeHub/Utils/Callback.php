<?php
/**
 * Callback.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Utils;


/**
 * Class Callback
 * @package WeHub
 */
final class Callback {

    /**
     * @var callable
     */
    private $callback;

    /**
     * Callback constructor.
     * @param $callback
     * @throws CallbackError
     */
    final public function __construct($callback) {
        if (!is_callable($callback)) {
            throw new CallbackError('parameter is not correct callable');
        }
        $this->callback = $callback;
    }

    /**
     * @param mixed ...$args
     * @return mixed
     */
    final public function apply(...$args) {
        return call_user_func_array($this->callback, $args);
    }

}
