<?php
/**
 * ValidatorBase.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


/**
 * Class ValidatorBase
 * @package WeHub\Validator
 */
abstract class ValidatorBase implements ValidatorInterface {

    /**
     * @var array
     */
    protected $validator_config;

    /**
     * ValidatorBase constructor.
     * @param array $config
     */
    final public function __construct(array $config) {
        $this->validator_config = $config;
    }

}
