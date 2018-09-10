<?php
/**
 * WxidValidator.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


/**
 * Class WxidValidator
 * @package WeHub\Action
 */
final class WxidValidator extends ValidatorBase {

    /**
     * @param $value
     * @param array $event
     * @return bool
     */
    final public function validate($value, array $event): bool {
        $app_id = $event['appid'] ?? null;
        $app_id_config = $this->validator_config[$app_id] ?? null;
        if (!is_array($app_id_config)) {
            return false;
        }
        return in_array($value, $app_id_config);
    }

}
