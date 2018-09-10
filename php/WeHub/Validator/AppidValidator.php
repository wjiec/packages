<?php
/**
 * AppidValidator.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


/**
 * Class AppidValidator
 * @package WeHub\Validator
 */
final class AppidValidator extends ValidatorBase {

    /**
     * @param $value
     * @param array $event
     * @return bool
     */
    final public function validate($value, array $event): bool {
        return in_array($value, array_keys($this->validator_config));
    }

}
