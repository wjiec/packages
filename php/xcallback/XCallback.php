<?php
/**
 * 更强大的回调方法
 *
 * @package   Jayson\XCallback
 * @author    Jayson <jayson@yycfd.com>
 * @copyright Copyright (C) 2019 Jayson
 * @license   MIT License
 */
declare(strict_types=1);


namespace Jayson\XCallback;
use Jayson\XCallback\CallbackException as Ex;


/**
 * Class XCallback
 * @package Jayson\XCallback
 */
class XCallback {

    /**
     * @var array|\Closure|string
     */
    protected $callback;

    /**
     * @var string
     */
    protected $hash;

    /**
     * @var bool
     */
    protected $strict;

    /**
     * XCallable constructor.
     * @param array|\Closure|string $object
     * @param string|null $method
     * @param bool $strict
     */
    public function __construct($object, ?string $method = null, bool $strict = false) {
        $this->strict = $strict;
        $this->makeSure($object, $method);
    }

    /**
     * @param bool $flag
     * @return XCallback
     */
    public function strictMode(bool $flag = true): self {
        $this->strict = $flag;
        return $this;
    }

    /**
     * @param mixed ...$args
     * @return mixed
     * @throws \ReflectionException
     */
    public function __invoke(...$args) {
        if ($this->strict) {
            if (!$this->getReflector()->validate($args)) {
                throw new Ex(sprintf('Bad callback invoke; arguments validate failure'));
            }
        }
        return call_user_func_array($this->callback, $args);
    }

    /**
     * @return string
     */
    public function getHash(): string {
        if (!$this->hash) {
            if (is_string($this->callback)) {
                $this->hash = sprintf('function#%s', $this->callback);
            } else if (is_array($this->callback)) {
                $method = $this->callback[1] ?: '???';
                if (is_object($this->callback[0])) {
                    $object = sprintf('object(%s)#%s', spl_object_hash($this->callback[0]),
                        get_class($this->callback[0]));
                } else {
                    $object = sprintf('class#%s', $this->callback[0]);
                }
                $this->hash = sprintf('%s::%s', $object, $method);
            } else {
                $this->hash = sprintf('closure(%s)', spl_object_hash($this->callback));
            }
        }
        return $this->hash;
    }

    /**
     * @return \Reflector
     * @throws \ReflectionException
     */
    public function getReflection(): \Reflector {
        $cb = &$this->callback;
        if (is_string($cb) || ($cb instanceof \Closure)) {
            return new \ReflectionFunction($cb);
        }

        if (is_string($cb[0])) {
            if (!method_exists($cb[0], $cb[1])) {
                return new \ReflectionClass($cb[0]);
            }
            return new \ReflectionMethod($cb[0], $cb[1]);
        }

        $object = new \ReflectionObject($cb[0]);
        if (!$cb[1]) {
            return $object;
        }
        return $object->getMethod($cb[1]);
    }

    /**
     * @return string
     */
    public function __toString(): string {
        return $this->getHash();
    }

    /**
     * @return Reflector
     * @throws \ReflectionException
     */
    protected function getReflector(): Reflector {
        return Reflector::reflect($this);
    }

    /**
     * @param array|\Closure|string $object
     * @param null|string $method
     * @return void
     */
    final private function makeSure($object, ?string $method = null): void {
        if ($object instanceof \Closure) {
            $this->callback = $object;
            return;
        }

        if (!$method || empty($method)) {
            if (is_string($object)) {
                if (strpos($object, '::') === false) {
                    if (!function_exists($object)) {
                        throw new Ex(sprintf('Bad callback; function %s not exists.', $object));
                    }
                    $this->callback = $object;
                    return;
                }

                list($object, $method) = explode('::', $object);
            }
        } else if (is_object($object)) {
            if (!method_exists($object, '__invoke')) {
                throw new Ex(sprintf('Bad callback; an object but without a known method'));
            }
            $method = '__invoke';
        } else if (is_array($object) && isset($object[0])) {
            if (!isset($object[1])) {
                $this->makeSure($object[0]);
            } else {
                $this->makeSure($object[0], $object[1]);
            }
            return;
        } else {
            throw new Ex('Bad callback');
        }

        $this->callback = array($object, $method);
    }

}
