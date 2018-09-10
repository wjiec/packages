<?php
/**
 * ActionValidator.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


use WeHub\Action\ActionNames;


/**
 * Class ActionValidator
 * @package WeHub\Validator
 */
final class ActionValidator implements ValidatorInterface {

    /**
     * @param $value
     * @param array $event
     * @return bool
     */
    final public function validate($value, array $event): bool {
        return ActionNames::has($value);
    }

}
