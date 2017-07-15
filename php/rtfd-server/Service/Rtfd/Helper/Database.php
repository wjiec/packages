<?php
/**
 * Database Helper
 */


class Rtfd_Helper_Database {
    /**
     * database configure
     *
     * @var array
     */
    private static $_server;

    /**
     * mysqli instance
     *
     * @var mysqli
     */
    private $_connection;

    /**
     * Rtfd_Helper_Database constructor.
     */
    public function __construct() {
        // check server configure
        if (!self::$_server || empty(self::$_server)) {
            throw new Rtfd_Exception_FatalError('database server information non initializing',
                'Rtfd:Helper:Database:__construct');
        }
    }

    /**
     * add database configure
     *
     * @param array $configure
     */
    public static function init_server(array $configure) {
        // configure from Rtfd_Config, is safe
        self::$_server = $configure;
    }

    /**
     * safe escape value
     *
     * @param $string
     * @return string
     */
    public function value_escape($string) {
        // create mysqli connection
        $this->_connect();
        // convert
        $string = Rtfd_Utils::to_string($string);
        // escape value
        return "'" . $this->_connection->escape_string($string) . "'";
    }

    /**
     * query sql
     *
     * @param $sql
     * @return mixed
     * @throws Rtfd_Exception_QueryError
     */
    public function fetch_all($sql) {
        // execute query
        $result = $this->_execute_query($sql);
        // check result type
        if (!($result instanceof mysqli_result)) {
            if (is_bool($result)) {
                throw new Rtfd_Exception_QueryError(Rtfd_Utils::Text($this->_connection->error),
                    'Rtfd:Helper:fetch_all');
            } else {
                throw new Rtfd_Exception_QueryError(Rtfd_Utils::Text($this->_connection->error),
                    'Rtfd:Helper:fetch_all');
            }
        }
        // fetch assoc array
        $assoc = $result->fetch_all(MYSQLI_ASSOC);
        // close result
        $result->close();
        // return result
        return $assoc;
    }

    /**
     * fetch assoc array and result is single row
     *
     * @param string $sql
     * @return array|bool
     */
    public function fetch_single_row($sql) {
        $result = self::fetch_all($sql);
        if (empty($result)) {
            return false;
        }
        return current($result);
    }

    /**
     * @param $sql
     * @return bool|mysqli_result
     * @throws Rtfd_Exception_QueryError
     */
    public function query($sql) {
        // execute query
        $result = $this->_execute_query($sql);
        // check result state
        if (!is_bool($result) || $result !== true) {
            throw new Rtfd_Exception_QueryError($this->_connection->error,
                'Rtfd:Helper:query');
        }
        // return result
        return $result;
    }

    /**
     * execute query and return query result
     *
     * @param $sql
     * @return bool|mysqli_result
     * @throws Rtfd_Exception_QueryError
     */
    private function _execute_query($sql) {
        // check sql is string
        if (!is_string($sql)) {
            throw new Rtfd_Exception_QueryError('sql except string, got ' . gettype($sql),
                'Rtfd:Helper:Database:_execute_query');
        }
        // connecting to server
        $this->_connect();
        // execute query
        return $this->_connection->query($sql);
    }

    /**
     * connecting to database server
     */
    private function _connect() {
        /* @TODO PDO */
        if ($this->_connection !== null) {
            return;
        }
        // init mysqli instance
        $this->_connection = mysqli_init();
        // set connect_timeout options
        $this->_connection->options(MYSQLI_OPT_CONNECT_TIMEOUT, _rtfd_database_connecting_timeout_);
        // real connecting to server
        $this->_connection->real_connect(
            self::$_server['host'],
            self::$_server['username'], self::$_server['password'],
            self::$_server['database'],
            self::$_server['port']
        );
        // check connecting error
        if ($this->_connection->connect_errno) {
            throw new Rtfd_Exception_ConnectingError(Rtfd_Utils::Text($this->_connection->connect_error),
                'Rtfd:Helper:Database:_connect');
        }
    }
}
