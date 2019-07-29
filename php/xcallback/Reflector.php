<?php
/**
 * 提供回调方法的参数验证和自动填充
 *
 * @package   Jayson\XCallback
 * @author    Jayson <jayson@yycfd.com>
 * @copyright Copyright (C) 2019 Jayson
 * @license   MIT License
 */
declare(strict_types=1);


namespace Jayson\XCallback;


/**
 * Class Reflector
 * @package Jayson\XCallable
 */
class Reflector {

    /**
     * @var XCallback
     */
    protected $callback;

    /**
     * @var array
     */
    protected $arguments;

    /**
     * @var self[]
     */
    protected static $reflectors = array();

    /**
     * Reflector constructor.
     * @param XCallback $callback
     * @throws \ReflectionException
     */
    final private function __construct(XCallback $callback) {
        $this->callback = $callback;
        $this->arguments = array();
        $this->reflectArguments();
    }

    /**
     * @param array $args
     * @return bool
     */
    final public function validate(array $args): bool {
        if (empty($this->arguments)) {
            return empty($args);
        }

        for ($index = 0; $index < count($this->arguments); ++$index) {
            $argument = $this->arguments[$index];
            if (!isset($args[$index])) {
                if ($argument['has_default'] || $argument['variadic']) {
                    return true;
                }
            } else {
                $value = &$args[$index];
                if ($value === null && $argument['nullable']) {
                    continue;
                }

                if ($argument['validator']($value)) {
                    continue;
                }

                return false;
            }
        }
        return true;
    }

    /**
     * @param array $args
     * @return array
     */
    final public function padArguments(array $args): array {
        return $args;
    }

    /**
     * @param XCallback $callback
     * @return Reflector
     * @throws \ReflectionException
     */
    final public static function reflect(XCallback $callback) {
        if (!isset(static::$reflectors[$callback->getHash()])) {
            static::$reflectors[$callback->getHash()] = new static($callback);
        }
        return static::$reflectors[$callback->getHash()];
    }

    /**
     * arguments reflection
     * @throws \ReflectionException
     */
    final private function reflectArguments(): void {
        /* @var \ReflectionFunctionAbstract $reflection */
        $reflection = $this->callback->getReflection();
        foreach ($reflection->getParameters() as $parameter) {
            $this->arguments[] = array(
                'name' => $parameter->getName(),
                'type' => $parameter->getType()->getName(),
                'validator' => $this->getTypeValidator($parameter->getType()->getName(), $parameter->getType()->isBuiltin()),
                'nullable' => $parameter->allowsNull(),
                'has_default' => $parameter->isDefaultValueAvailable(),
                'default' => $parameter->isDefaultValueAvailable() ? $parameter->getDefaultValue() : null,
                'variadic' => $parameter->isVariadic()
            );
        }
    }

    /**
     * @param string $type
     * @param bool $builtin
     * @return XCallback
     */
    final private function getTypeValidator(string $type, bool $builtin = false) {
        if ($builtin) {
            switch ($type) {
                case 'string': return new XCallback('is_string');
                case 'int': return new XCallback('is_int');
                case 'float': return new XCallback('is_float');
                case 'double': return new XCallback('is_double');
                case 'bool': return new XCallback('is_bool');
                case 'array': return new XCallback('is_array');
                default: return new XCallback(function() { return true; });
            }
        }

        return new XCallback(function($value) use ($type) {
            return $value instanceof $type;
        });
    }

}
