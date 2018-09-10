<?php
/**
 * ValidatorInterface.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


/**
 * Interface ValidatorInterface
 * @package WeHub\Validator
 */
interface ValidatorInterface {

    /**
     * @param $value
     * @param array $event
     * @return bool
     */
    public function validate($value, array $event): bool;

}
