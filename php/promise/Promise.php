<?php
/**
 * PHP版本的Promise, 提供更加简单的方式进行操作
 *
 * @package   Jayson\Promise
 * @author    Jayson <jayson@yycfd.com>
 * @copyright Copyright (C) 2019 Jayson
 * @license   MIT License
 */
declare(strict_types=1);


namespace Jayson\Promise;
use Jayson\XCallback\XCallback;


/**
 * Class Promise
 * @package Jayson\Promise
 */
class Promise {

    /**
     * @var XCallback
     */
    protected $callback;

    /**
     * Promise constructor.
     * @param XCallback $callback
     */
    public function __construct(XCallback $callback) {
        $this->callback = $callback;
    }

    /**
     * @param XCallback $resolve
     * @param XCallback|null $reject
     * @return mixed|Promise
     * @throws \ReflectionException
     */
    public function then(XCallback $resolve, ?XCallback $reject = null) {
        return $this->callback->__invoke($resolve, $reject);
    }

    /**
     * @param $data
     * @return XCallback
     */
    public static function resolve($data): XCallback {
        return new XCallback(function(XCallback $resolve, XCallback $reject) use ($data) {
            return $resolve($data);
        });
    }

    /**
     * @param $data
     * @return XCallback
     */
    public static function reject($data): XCallback {
        return new XCallback(function(XCallback $resolve, XCallback $reject) use ($data) {
            return $reject($data);
        });
    }

}
