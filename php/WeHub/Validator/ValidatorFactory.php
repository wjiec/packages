<?php
/**
 * ValidatorFactory.php
 *
 * @package   yifen-robot
 * @author    ShadowMan <shadowman@shellboot.com>
 * @copyright Copyright (C) 2016-2018 ShadowMan
 * @license   MIT License
 */
namespace WeHub\Validator;


/**
 * Class ValidatorFactory
 * @package WeHub\Validator
 */
final class ValidatorFactory {

    /**
     * @var array
     */
    private $validator_config;

    /**
     * ValidatorFactory constructor.
     * @param array $config
     */
    final public function __construct(array $config) {
        $this->validator_config = $config;
    }

    /**
     * @param string $validator_name
     * @return ValidatorInterface
     * @throws ValidatorError
     */
    final public function getValidator(string $validator_name): ValidatorInterface {
        $validator_class = sprintf('%s\%sValidator', __NAMESPACE__, ucfirst($validator_name));
        if (!class_exists($validator_class)) {
            throw new ValidatorError(sprintf('validator not found for %s', $validator_name));
        }
        return new $validator_class($this->validator_config);
    }

}
