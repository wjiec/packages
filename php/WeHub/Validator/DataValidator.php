<?php
/**
 * DataValidator.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


/**
 * Class DataValidator
 * @package WeHub\Validator
 */
class DataValidator extends ValidatorBase {

    /**
     * @param $value
     * @param array $event
     * @return bool
     */
    final public function validate($value, array $event): bool {
        return is_array($value);
    }

}
