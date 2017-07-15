<?php
/**
 * Redis Helper for RtfdTools
 */


class Rtfd_Helper_Redis {
    /**
     * redis connection instance
     *
     * @var Redis
     */
    private $_connection;

    /**
     * Rtfd_Helper_Redis constructor.
     */
    public function __construct() {
        // check server configure
        if (!self::$_server || empty(self::$_server)) {
            throw new Rtfd_Exception_FatalError('redis server information non initializing',
                'Rtfd:Helper:Redis:__construct');
        }
    }

    /**
     * push item to Redis server
     *
     * @param $key
     * @param $value
     * @param bool $serialize
     * @throws Rtfd_Exception_CacheError
     */
    public function push($key, $value, $serialize = false) {
        // connecting to server
        $this->_connect();
        // setting item
        if (!$this->_connection->set($key, $serialize ? serialize($value) : $value)) {
            throw new Rtfd_Exception_CacheError($this->_connection->getLastError(), 'Rtfd:Helper:Redis:push');
        }
    }

    /**
     * push item with expired time
     *
     * @param string $key
     * @param mixed $value
     * @param int $expired
     * @param bool $serialize
     * @throws Rtfd_Exception_CacheError
     */
    public function push_ex($key, $value, $expired, $serialize = false) {
        // connecting to server
        $this->_connect();
        // setting item
        if (!$this->_connection->setex($key, intval($expired), $serialize ? serialize($value) : $value)) {
            throw new Rtfd_Exception_CacheError($this->_connection->getLastError(), 'Rtfd:Helper:Redis:push_ex');
        }
    }

    /**
     * from cache getting item
     *
     * @param string $key
     * @param bool $serialize
     * @return mixed
     */
    public function get($key, $serialize = false) {
        // connecting to server
        $this->_connect();
        // getting value
        $value = $this->_connection->get($key);
        // serialize
        if ($value === false) {
            return $value;
        } else if ($serialize === true) {
            return unserialize($value);
        }
        return $value;
    }

    /**
     * getting cache keys
     *
     * @return array
     */
    public function get_cached_keys() {
        // connecting to server
        $this->_connect();
        // getting keys
        return $this->_connection->keys('*');
    }

    /**
     * push array to cache server
     *
     * @param string $key
     * @param array $array
     * @throws Rtfd_Exception_CacheError
     */
    public function push_array($key, array $array) {
        // connecting to server
        $this->_connect();
        // foreach all item
        if (!call_user_func(array($this->_connection, 'rPush'), array_merge(array($key), $array))) {
            throw new Rtfd_Exception_CacheError('Key exists and is not a list', 'Rtfd:Helper:Cache:push_array');
        }
    }

    /**
     * getting array from cache
     *
     * @param string $key
     * @return array
     */
    public function get_array($key) {
        // connecting to server
        $this->_connect();
        // return array
        return $this->_connection->lRange($key, 0, -1);
    }

    /**
     * delete item from cache
     */
    public function delete_item(/* keys */) {
        // getting args
        $args = func_get_args();
        // is array
        if (is_array($args[0])) {
            $args = $args[0];
        }
        // connecting to server
        $this->_connect();
        // delete item
        $this->_connection->delete($args);
    }

    /**
     * init redis server
     *
     * @param array $configure
     */
    public static function init_server(array $configure) {
        // init from GM_Core, no qa
        self::$_server = $configure;
    }

    /**
     * connecting to redis server
     */
    private function _connect() {
        if (!($this->_connection instanceof Redis)) {
            // create Redis instance
            $this->_connection = new Redis();
            // check connecting
            if ($this->_connection->ping() === '+PONG') {
                return;
            }
        }
        // connecting to server
        $this->_connection->connect(self::$_server['host'], self::$_server['port'],
            _rtfd_database_connecting_timeout_);
        // check connecting error
        if ($this->_connection->ping() === 'PONG') {
            return;
        }
        throw new Rtfd_Exception_CacheError($this->_connection->getLastError(), 'Rtfd:Helper:Redis:_connect');
    }

    /**
     * cache server configure
     *
     * @var array
     */
    private static $_server;
}
